(ns blog.routes.pearson
  (:require [compojure.core :refer :all]
            ;;helper
            [blog.routes.helper :as helper]
            ;;validacija
            [noir.validation :refer [rule errors? has-value? on-error]]

            ;;baza
            [blog.models.db :as db]))

(db/read-ratings)


(db/get-rating 5)

(reduce (fn [my-map value]
            (assoc my-map value (/ 1 value)))
          {}
          [1 2 3 4 5])


(reduce (fn [my-map value]
           (assoc my-map value (* value value)))
        {}
        [1 2 3 4 5])

(defn ratings-coll []
  "Returning regular collection from database"
  (for [{:keys [id_blog rat_val name id]}(db/read-ratings)]
    (list id_blog  rat_val name)))

(ratings-coll)

(def proba {:5 {:Marko 2 :Jelena 3}})

(get (get proba :5) :Marko)

(defn foo [m f]
  (into {} (for [[k v] m] [k (f v)])))

(foo {:a "test" :b "testing"} #(.toUpperCase %))



;;BITNO

(defn print-seq [s]
  (when (seq s)
    (prn (first s))
    (recur (rest s))))

;;destructuring with a vector

(def guys-whole-name ["Guy" "lewis" "Steele"])

(str (nth guys-whole-name 2) ", "
     (nth guys-whole-name 0) " "
     (nth guys-whole-name 1))

(let [[f-name m-name l-name] guys-whole-name]
  (str l-name ", " f-name " " m-name))

;;drugi primer
(def guys-name-map
    {:f-name "Guy" :m-name "Lewis" :l-name "Steele"})

(let [{f-name :f-name, m-name :m-name, l-name :l-name } guys-name-map]
    (str l-name ", " f-name " " m-name))
