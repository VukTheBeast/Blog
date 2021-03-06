(ns blog.routes.post
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            [blog.routes.helper :as helper]
            [blog.routes.slopeone :as slope]))



(defn post [id]
  (layout/common
    (helper/navbar)
    (helper/header ((db/get-blog id) :title) ((db/get-blog id) :subtitle) "/img/post-bg.jpg")
  [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:div.jumbotron
      [:span.id {:style "display:none"} id ]
      [:span.meta "Posted by " [:a {:href "#"} ((db/get-blog id) :postman)] " on " (helper/format-time ((db/get-blog id) :timestamp))]
      [:p
      ((db/get-blog id) :blog_content)]
      [:ul.pager
       [:li.next
        [:a {:href (str "/comment/" id)} "Comment &rarr;"]]]
       [:ul.pager
       [:li.next
        [:a {:href (str "/rate/" id)} "Rate &rarr;"]]]
       [:ul.pager
        [:li.next
         [:form {:method "GET" :action (str "/rate/" id)}]]]]
      [:br]
      [:div
       [:h2 "Comments"]
       (helper/show-comments id)]]
     [:div.col-lg-2.col-md-2
      [:span.meta "Recommendet posts:"]
      [:span (for [{:keys [id-predicted]}(slope/return-predictions-ids id)]
               [:a {:href (str "/post/"id-predicted)} (db/get-blog-title id-predicted)])]]]]
    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]))

(defroutes post-routes
  (GET "/post/:id" [id] (post id)))
