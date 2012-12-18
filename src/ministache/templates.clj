(ns ministache.templates
  (:require [net.cgrand.enlive-html :refer [deftemplate defsnippet content any-node]]))

(deftemplate page "page.html" [styles scripts cnt]
  [:div#content] (content cnt))

(defsnippet welcome-page "welcome.html" [:body :> any-node] [])

(defsnippet clojure-page "clojure.html" [:body :> any-node] [])

(defsnippet example-form "form.html" [:body :> any-node] [])

(defsnippet javascript-playground-page "javascript.html" [:body :> any-node] [])

(defsnippet admin-page "admin.html" [:body :> any-node] [])