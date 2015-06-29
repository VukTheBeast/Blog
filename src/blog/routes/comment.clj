(ns blog.routes.comment
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer :all]
            ;;redirect namespace
            [noir.response :refer [redirect]]

            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            [blog.routes.helper :as helper]))


(defn page [id_blog]
  (layout/common
    (helper/navbar)
    (helper/header "Comment" "Be nice!" "/img/comment-bg.jpg")
  ;;main
  [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:p "Want to sey something smart? Fill out the form below to comment."]
      [:form#contactForm
       [:input.id {:name "id_blog" :style "display:none" :value id_blog}]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Name & Last Name"]
         [:input.form-control {:type "text" :id "name" :placeholder "Name" :required "required" :data-validation-required-message "Please enter your name and last name."}]
         [:p.help-block.text-danger]]]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Message"]
         [:textarea.form-control { :id "message" :placeholder "Text" :required "required" :data-validation-required-message "Please enter a text."}]
         [:p.help-block.text-danger]]]
       [:br]
       [:div#success]
       [:div.row
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:button.btn.btn-default.submit "Comment"]]]
       ]

      ]

     ]]
    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]
   [:script {:type "text/javascript" :src "/js/comment.js"}]
     )

    )

(defn save-comment [id name message]
  (db/save-comment name message id)
  ;;(redirect "/post/" id)
  )


(defroutes comment-routes
  (GET "/comment/:id" [id] (page id))
  (POST "/comment" [id name message] (save-comment id name message))
)
