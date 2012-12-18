(ns ministache.web
  (:require [ministache.templates :as temp]
            [net.cgrand.moustache :refer [app]]
            [clojure.java.io :as io]
            [ring.middleware.http-basic-auth :refer [wrap-with-auth wrap-require-auth]]
            [ring.middleware.stacktrace :as trace]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn wrap-error-page [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "text/html"}
            :body (slurp (io/resource "500.html"))}))))

(defn view-frontpage
  [r]
  (->> (temp/page nil nil (temp/welcome-page))
       response))

(defn view-clojure-page
  [r]
  (->> (temp/page nil nil (temp/clojure-page))
       response))

(defn view-example-form
  [r]
  (->> (temp/page nil nil (temp/example-form))
       response))

(defn view-javascript-playground
  [r]
  (->> (temp/page nil nil (temp/javascript-playground-page))
       response))

(defn view-admin-page
  [r]
  (->> (temp/page nil nil (temp/admin-page))
       response))

(defn authenticate [username password]
  (when (and (= username "webdriver")
             (= password "test"))
    {:username username}))

(def routes
  (app
   [""] view-frontpage
   ["clojure"] view-clojure-page
   ["example-form"] view-example-form
   ["js-playground"] view-javascript-playground
   ["admin" &] (app
                (wrap-with-auth authenticate)
                (wrap-require-auth authenticate
                                   "Admin Area"
                                   {:body "You must enter correct credentials to view this area."})
                [""] view-admin-page)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (-> #'routes
                         ((if (env :production)
                            wrap-error-page
                            trace/wrap-stacktrace)))
                     {:port port :join? false})))