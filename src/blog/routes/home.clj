(ns blog.routes.home
  (:require [compojure.core :refer :all]
            [blog.views.layout :as layout]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.session :as session]
            ;;validacija
            [noir.validation :refer [rule errors? has-value? on-error]]

            [blog.routes.helper :as helper]))



(defn show-guests []
  [:ul.guests
    (for [{:keys [message name timestamp]} (db/read-guests)]
      [:li
      [:blockquote message]
      [:p "-" [:cite name]]
      [:time (helper/format-time timestamp)]])])


(defn home [& [flag]]
  (layout/common
    (helper/navbar)
    (helper/header "Vuk Blog" "A  Blog developed in clojure technology" "img/home-bg.jpg")
   ;;mein content
   [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      (helper/show-blogs flag)
      [:ul.pager
       [:li.next
        (cond
        (has-value? flag )()
        :else [:a {:href "/all"} "Older Posts &rarr;"]
        )
        ]]]]]
    [:hr]

    [:footer
     [:div.container
      [:div.row
       [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
        [:ul.list-inline.text-center
         (helper/social-button)]
        [:p.copyright.text-muted "Copyright &copy; www.blog.vuk"]]]]]
     )

    )


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

(defn foo-handler []
  "foo pozvan")

(defn bar-handler [param]
  (str "Pozvan post sa parametrom: " param))

(defn display-profile [id]
  (str "profile sa id " id))

(defn display-settings [id]
  (str "settings sa id " id))

(defn change-password-page [id]
  (str "change-password-page sa id " id))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/:flag" [flag] (home flag) )
  (POST "/" [name message] (save-message name message))
  (GET "/vuk" [] (foo-handler))
  (POST "/vuk" [param] (bar-handler param))
  (context "/user/:id" [id]
    (GET "/profile" [] (display-profile id))
    (GET "/settings" [] (display-settings id))
    (GET "/change-password" [] (change-password-page id)))
  )


;;http://www.lispcast.com/hiccup-tips
