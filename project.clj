(defproject blog "0.1.0-SNAPSHOT"
  :description "This is Blog web application developed in Clojure for Web development"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 ;;JDBC dependencies
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [lib-noir "0.7.6"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler blog.handler/app
         :init blog.handler/init
         :destroy blog.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
