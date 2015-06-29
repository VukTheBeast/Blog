(ns blog.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            ;;routes
            [blog.routes.home :refer [home-routes]]
            [blog.routes.about :refer [about-routes]]
            [blog.routes.contact :refer [contact-routes]]
            [blog.routes.post :refer [post-routes]]
            [blog.routes.comment :refer [comment-routes]]
            [blog.routes.auth :refer [auth-routes]]
            [blog.routes.admin :refer [admin-routes]]

            ;;session
            [noir.session :as session]
            [ring.middleware.session.memory :refer [memory-store]]
            ;;model
            [blog.models.db :as db]
            ;;validacija
            [noir.validation :refer [wrap-noir-validation]]))


(defn init []
  (println "blog is starting")
  ;;u slucaju da ne postji tabela, napravi je
  (if-not (.exists (java.io.File. "./db.sq3"))
    (db/create-guestbook-table))
  )

(defn destroy []
  (println "blog is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (->
   (handler/site
   (routes
       auth-routes

       about-routes
       contact-routes
       post-routes
       comment-routes
       admin-routes
       home-routes
       app-routes))
      ;;(wrap-base-url))
    (session/wrap-noir-session
     {:store (memory-store)})
   (wrap-noir-validation)))
