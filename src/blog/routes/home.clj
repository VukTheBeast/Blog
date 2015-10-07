(ns blog.routes.home
  (:require [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            [noir.validation :refer [rule errors? has-value? on-error]]
            [blog.routes.helper :as helper]))

(defn home
  "Main Blog home page, optional param for displaying all blogs
   by default it display 3 newst blogs"
  [& [flag]]
  (layout/common
    (helper/navbar)
    (helper/header "Vuk Blog" "A  Blog developed in clojure technology" "img/home-bg.jpg")
   [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      (helper/show-blogs flag)
      [:ul.pager
       [:li.next
        (cond
        (has-value? flag )()
        :else [:a {:href "/all"} "Older Posts &rarr;"])]]]]]
    [:hr]
    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]))


(defn save-message [name message]
  (cond
    (empty? name)
    (home name message "Some dummy forgot to leave a name")
    (empty? message)
    (home name message "Don't you have something to say?")
    :else
    (do
      (db/save-message name message)
      (home))))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/:flag" [flag] (home flag) )
  (POST "/" [name message] (save-message name message)))

