(ns blog.routes.admin
  (:require [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            [blog.routes.helper :as helper]
            ;;validacija
            [noir.validation :refer [rule errors? has-value? on-error]]
            ;;redirect
            [noir.response :refer [redirect]]))

(defn navbar []
  (list [:nav.navbar.navbar-default.navbar-custom.navbar-fixed-top
   [:div.container-fluid
    ;;kada su mobile dimenzije da se napravi ikonica
    [:div.navbar-header.page-scroll
     [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target "#bs-example-navbar-collapse-1"}
      [:span.sr-only "Toggle navigation"]
      (take 3 (repeat [:span.icon-bar]))]
     [:a.navbar-brand {:href "/"} "Admin"]]
    ;;nav linkovi
    [:div.collapse.navbar-collapse {:id "bs-example-navbar-collapse-1"}
     [:ul.nav.navbar-nav.navbar-right
      [:li [:a {:href "/"} "Home"]]
      [:li [:a {:href "/logout"} "Logout"]]]]
    ]

   ]))




(defn admin-page [& [success]]
  (layout/common
   (navbar)
   (helper/header "Admin Panel" "Add your awesome Blog" "img/admin-bg.jpg")
   [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      (cond
       (has-value? success)
        [:div.alert.alert-success success])
      [:form#BlogForm {:action "admin" :method "POST" :novalidate ""}
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Title"]
         [:input.form-control {:type "text" :id "title" :name "title" :placeholder "Add Title" :required "required" :data-validation-required-message "Please enter blog title."}]
         [:p.help-block.text-danger]]]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Subtitle"]
         [:input.form-control {:type "text" :id "subtitle" :name "subtitle" :placeholder "Subtitle" :required "required" :data-validation-required-message "Please enter blog subsubtitle."}]
         [:p.help-block.text-danger]]]
       [:div.row.control-group
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:label "Content"]
         [:textarea.form-control {:type "text" :id "content" :name "content" :placeholder "Content" :required "required" :data-validation-required-message "Please enter blog content."}]
         [:p.help-block.text-danger]]]
       [:br]
       [:div#success]
       [:div.row
        [:div.form-group.col-xs-12.floating-label-form-group.controls
         [:button.btn.btn-default.submit "Add blog"]]]
       ]

      ]

     ]]

   ))

(defn logout []
  (session/clear!)
  (redirect "/"))

(defn add-blog [title subtitle content]
  (db/save-blog title subtitle "Admin" content)
  (admin-page "You successfuly add your awesome blog!")
  )


(defroutes admin-routes
  (GET "/admin" []
       (if (= "admin" (session/get :user))
       (admin-page)
       (redirect "/login")))
  (POST "/admin" [title subtitle content] (add-blog title subtitle content))
  (GET "/logout" [] (logout))
)
