(ns blog.routes.contact
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [blog.routes.helper :as helper]))


(defn contact []
  (layout/common
    (helper/navbar)
    (helper/header "Contact Me" "Have questions? I have answers (maybe)." "img/contact-bg.jpg")
  [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:p "Want to get in touch with me? Fill out the form below to send me a message and I will try to get back to you within 24 hours!"]
      [:form#contactForm {:name "sentMessage" :novalidate ""}
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Name"]
         [:input.form-control {:type "text" :id "name" :placeholder "Name" :required "required" :data-validation-required-message "Please enter your name."}]
         [:p.help-block.text-danger]]]

        [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Email Address"]
         [:input.form-control {:type "email" :id "email" :placeholder "Email Addresss" :required "required" :data-validation-required-message "Please enter your email address."}]
         [:p.help-block.text-danger]]]

       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Phone Number"]
         [:input.form-control {:type "tel" :id "phone" :placeholder "Phone Number" :required "required" :data-validation-required-message "Please enter your phone number."}]
         [:p.help-block.text-danger]]]

       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Message"]
         [:textarea.form-control { :id "message" :placeholder "Message" :required "required" :data-validation-required-message "Please enter a message."}]
         [:p.help-block.text-danger]]]
       [:br]
       [:div#success]
       [:div.row
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:button.btn.btn-default {:type "submit"} "Send"]]]]]]]
    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]))

(defroutes contact-routes
  (GET "/contact" [] (contact))
)
