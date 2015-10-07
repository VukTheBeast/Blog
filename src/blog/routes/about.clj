(ns blog.routes.about
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [blog.routes.helper :as helper]))


(defn about []
  (layout/common
    (helper/navbar)
    (helper/header "About Me" "This is what I do" "img/about-bg.jpg")
  ;;main
  [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      (helper/about-content)]]]

    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]))

(defroutes about-routes
  (GET "/about" [] (about)))
