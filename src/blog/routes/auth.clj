(ns blog.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [blog.views.admin :as admin]
            [blog.views.layout :as layout]
            [hiccup.form :refer
            [form-to label text-field password-field submit-button]]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            ;;helper
            [blog.routes.helper :as helper]
            ;;validacija
            [noir.validation :refer [rule errors? has-value? on-error]]
            ;;enkripcija
            [noir.util.crypt :as crypt]
            ;;baza
            [blog.models.db :as db]))


;;helper function

(defn format-error [[error]]
  [:p.error error])

(defn control [field name text]
  (list (on-error name format-error)
      (label name text)
      (field name)
      [:br]))


(defn registration-page []
  (admin/common
    (form-to [:post "/register"]
      (control text-field :id "screen name")
      (control password-field :pass "Password")
      (control password-field :pass1 "Retype Password")
      (submit-button "Create Account"))))

(defn handle-registration [id pass pass1]
  (rule (= pass pass1)
      [:pass "password was not retyped correctly"])
  (if (errors? :pass)
    (registration-page)
    (do
      (db/add-user-record {:id id :pass (crypt/encrypt pass)})
      (redirect "/login"))))


(defn login-page [& [error]]
  (admin/common
   (helper/header "Log In" "Please enter your username and pass for admin panel." "img/login-bg.jpg")
   [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:div.alert.alert-success "To login enter username: admin password: admin"]
      (cond
       (has-value? error)
        [:div.alert.alert-danger error])
      [:form#LogInForm {:action "login" :method "POST"}
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Username"]
         [:input.form-control {:type "text" :id "username" :name "id" :placeholder "Username" :required "required" :data-validation-required-message "Please enter your username."}]
         [:p.help-block.text-danger]]]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Password"]
         [:input.form-control {:type "password" :id "password" :name "pass" :placeholder "Password" :required "required" :data-validation-required-message "Please enter your password."}]
         [:p.help-block.text-danger]]]
       [:br]
       [:div#success]
       [:div.row
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:button.btn.btn-default.submit "Login"]]]
       ]

      ]

     ]]

   ))

(comment
(defn handle-login [id pass]
  (let [user (db/get-user id)]
    (rule (has-value? id)
      [:id "screen name is required"])
    (rule (has-value? pass)
      [:pass "password is required"])
    (rule (and user (crypt/compare pass (:pass user)))
      [:pass "invalid password"])
    (if (errors? :id :pass)
    (login-page)
      (do
        (session/put! :user id)
        (redirect "/")))))
  )

(defn handle-login [id pass]
  (cond
    (empty? id)
    (login-page "screen name is required")
    (empty? pass)
    (login-page "password is required")
    (and (= "admin" id) (= "admin" pass))
    (do
    (session/put! :user id)
    (redirect "/admin"))
    :else
    (login-page "authentication failed")))


(defroutes auth-routes
  (GET "/register" [_] (registration-page))
  (POST "/register" [id pass pass1]
        (handle-registration id pass pass1))
  (GET "/login" [] (login-page))
  (POST "/login" [id pass]
        (handle-login id pass))
  )
