(ns blog.routes.rate
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer :all]
            [noir.response :refer [redirect]]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            [blog.routes.helper :as helper]))


(defn page [id_blog]
  (layout/common
    (helper/navbar)
    (helper/header "Rate" "You can do it!" "/img/comment-bg.jpg")
  [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:p "If you think it could be rated, then do it!"]
      [:form#rateForm
       [:input.id {:name "id_blog" :style "display:none" :value id_blog}]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Name & Last Name"]
         [:input.form-control {:type "text" :id "name" :placeholder "Name" :required "required" :data-validation-required-message "Please enter your name and last name."}]
         [:p.help-block.text-danger]]]

       [:fieldset.rating {:id "rating"}
          [:input {:type "radio" :id "star5" :name "rating" :class "rate" :value "5"}][:label {:for "star5" :title "Rocks!"} "5 stars"]
          [:input {:type "radio" :id "star4" :name "rating" :class "rate" :value "4"}][:label {:for "star4" :title "Preaty good"} "4 stars"]
          [:input {:type "radio" :id "star3" :name "rating" :class "rate" :value "3"}][:label {:for "star3" :title "Meh"} "3 stars"]
          [:input {:type "radio" :id "star2" :name "rating" :class "rate" :value "2"}][:label {:for "star2" :title "Kinda bad"} "2 stars"]
          [:input {:type "radio" :id "star1" :name "rating" :class "rate" :value "1"}][:label {:for "star1" :title "Suck big time"} "1 stars"]]
       [:br]
       [:div#success]
       [:div.row
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:button.btn.btn-default.submit "Rate"]]]]]]]
    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]))

(defn save-rate [id name rat_val]
  (db/save-rating name rat_val id))


(defroutes rate-routes
  (GET "/rate/:id" [id] (page id))
  (POST "/rate" [id name rat_val] (save-rate id name rat_val)))
