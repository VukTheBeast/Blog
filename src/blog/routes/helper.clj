(ns blog.routes.helper
  (:require [compojure.core :refer :all]
            [hiccup.form :refer :all]
            [blog.models.db :as db]
            [noir.validation :refer [rule errors? has-value? on-error]]
            [noir.session :as session]))

(defn format-time
  "Format timestamp to propriet date"
  [timestamp]
  (-> "dd/MM/yyyy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn format-time-sec
  "Format timestamp to propriet datetime"
  [timestamp]
  (-> "dd/MM/yyyy HH:mm:ss"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn navbar
  "Rendering a navbar component to a layout"
  []
  (list [:nav.navbar.navbar-default.navbar-custom.navbar-fixed-top
   [:div.container-fluid
    ;;kada su mobile dimenzije da se napravi ikonica
    [:div.navbar-header.page-scroll
     [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target "#bs-example-navbar-collapse-1"}
      [:span.sr-only "Toggle navigation"]
      (take 3 (repeat [:span.icon-bar]))]
     [:a.navbar-brand {:href "http://clojure.org/"} "Clojure"]]
    ;;nav linkovi
    [:div.collapse.navbar-collapse {:id "bs-example-navbar-collapse-1"}
     [:ul.nav.navbar-nav.navbar-right
      [:li [:a {:href "/"} "Home"]]
      [:li [:a {:href "/about"} "About"]]
      [:li [:a {:href "/contact"} "Contact"]]
      [:li [:a {:href "/login"} "Admin"]]]]]]))


(defn header
  "Rendering header"
  [head subheading url]
  [:header.intro-header {:style (str "background-image:url('"url"')")}
   [:div.container
    [:div.row
     [:div.col-lg-8.col-lg-offset-2.col-md-10.col-md-offset-1
      [:div.site-heading
       [:h1 head]
       [:hr.small]
       [:span.subheading subheading]]]]]])

(defn show-avg-rating
  "Show avrage ratings blog and number of graders"
  [id]
  (for [{:keys [numplp avgvalue]}(blog.models.db/get-rating-avg id)]
    [:text (str " [⋆ " avgvalue "/5 rated by " numplp " people]")]))


(defn show-blogs
  "Display blogs on home page. By default it display 3 newest blogs,
   optional shows all blogs"
  [& [all]]
  (for [{:keys [title subtitle timestamp blog_content id postman]}
        (cond
         (has-value? all) (blog.models.db/read-blogs)
         :else (take 3 (blog.models.db/read-blogs)))]
        [:div.post-preview
         [:a {:href (str "/post/" id)}
          [:h2.post-title title]
          [:h3.post-subtitle subtitle]]
         [:p.post-meta "Posted by " [:a {:href "#"} postman " on "] (format-time timestamp) (show-avg-rating id)]]))



(defn show-comments
  "Display comment in formated html of blog with uniq id"
  [id]
  (for [{:keys [message comment_owner timestamp]} (blog.models.db/get-comment id)]
    [:div.post-preview
    [:h3.post-subtitle message]
    [:p.post-meta "Comment by " comment_owner " at " (format-time-sec timestamp)]
    [:hr]]))

(defn about-content
  "Display about conntent"
  []
  (list [:p "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Saepe nostrum ullam eveniet pariatur voluptates odit, fuga atque ea nobis sit soluta odio, adipisci quas excepturi maxime quae totam ducimus consectetur?"]
  [:p "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eius praesentium recusandae illo eaque architecto error, repellendus iusto reprehenderit, doloribus, minus sunt. Numquam at quae voluptatum in officia voluptas voluptatibus, minus!"]
  [:p "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nostrum molestiae debitis nobis, quod sapiente qui voluptatum, placeat magni repudiandae accusantium fugit quas labore non rerum possimus, corrupti enim modi! Et."]))

(defn flat-vec
  "Converts a map
   to a flat vector"
  [coll]
  (reduce #(apply conj %1 %2)
          []
          coll))

(defn map-vals
  "mutating the values of a given map, using transient function"
  [f m]
  (persistent!
   (reduce (fn [m [k v]]
             (assoc! m k (f k v)))
           (transient m) m)))

(defn map-nested-vals
  "applyed map-vals for sublevel fo map, nested map in this case"
  [f m]
  (map-vals
   (fn [k1 inner-map]
     (map-vals
      (fn [k2 val] (f [k1 k2] val)) inner-map)) m))

(defn mapmap
  "appling two functions to a
   given sequence and returns a map with keys,if  a single function
   is passed to the mapmap function, it uses the identity function to generate keys in
   the map returned by it."
  ([vf s]
   (mapmap identity vf s))
  ([kf vf s]
   (zipmap (map kf s)
           (map vf s))))

(defn social-button
  "Rendering social buttons for footer, for better maintenace"
  []
  (list [:li
    [:a {:href "https://www.facebook.com/vuk.manic.9"}
     [:span.fa-stack.fa-lg
      [:i.fa.fa-circle.fa-stack-2x]
      [:i.fa.fa-facebook.fa-stack-1x.fa-inverse]]]]
  [:li
    [:a {:href "/"}
     [:span.fa-stack.fa-lg
      [:i.fa.fa-circle.fa-stack-2x]
      [:i.fa.fa-twitter.fa-stack-1x.fa-inverse]]]]
  [:li
    [:a {:href "https://github.com/VukTheBeast/Blog"}
     [:span.fa-stack.fa-lg
      [:i.fa.fa-circle.fa-stack-2x]
      [:i.fa.fa-github.fa-stack-1x.fa-inverse]]]]))
