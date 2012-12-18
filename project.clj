(defproject ministache "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://ministache.herokuapp.com"
  :license {:name "FIXME: choose"
            :url "http://example.com/FIXME"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring "1.0.2"]
                 [ring-http-basic-auth "0.0.2"]
                 [net.cgrand/moustache "1.0.0"]
                 [enlive "1.0.0"]
                 [environ "0.2.1"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :profiles {:production {:env {:production true}}})