(ns blog.routes.slopeone
  (:require [compojure.core :refer :all]
            [blog.models.formatteddata :as dbf]
            [blog.routes.helper :as hlp]))



(defn slope-one
  "function to produce
   a mathematical model from the data to use for slope one algorithm,
    basicly finds the differences between
   the ratings of all the items in the model using the for macro"
  [data]
  (let [diff-map (for [[user preferences] data]
                   (for [[i u-i] preferences
                         [j u-j] preferences
                         :when (and (not= i j)
                                    u-i u-j)]
                     [[i j] (- u-i u-j)]))
        diff-vec (hlp/flat-vec diff-map)
        update-fn (fn [[freqs-so-far diffs-so-far]
                       [item-pair diff]]
                    [(update-in
                      "replace the value of a key
                       in a map"
                        freqs-so-far
                        item-pair (fnil inc 0))
                     (update-in diffs-so-far
                                item-pair (fnil + 0) diff)])
        [freqs
         total-diffs] (reduce update-fn
                              [{} {}] diff-vec)
        differences (hlp/map-nested-vals
                     (fn [item-pair diff]
                       (/ diff (get-in freqs item-pair)))
                     total-diffs)]
    {:freqs freqs
     :differences differences}))





(defn predict
  "retrieve the sum of frequencies and differences of each item in the maps returned by
   the slope-one function"
  [{:keys [differences freqs]
    :as model}
   preferences
   item]
  (let [get-rating-fn (fn [[num-acc denom-acc]
                           [i rating]]
                        (let [freqs-ji (get-in freqs [item i])]
                          [(+ num-acc
                              (* (+ (get-in differences [item i])
                                    rating)
                                 freqs-ji))
                           (+ denom-acc freqs-ji)]))]
    (->> preferences
         (filter #(not= (first %) item))
         (reduce get-rating-fn [0 0])
         (apply /))))

(defn known-posts
  "returns the names of
   all posts id in the model data"
  [model]
  (-> model :differences keys))

(defn recommodations
  ([model preferences]
   (recommodations
    model
    preferences
    (filter #(not (contains? preferences %))
            (known-posts model))))
  ([model preferences items]
   (hlp/mapmap (partial predict model preferences)
           items)))

(def slope-model (slope-one dbf/data))

(defn return-predictions-ids
  [id]
  post_ids (recommodations slope-model {:(str id) 3}))
