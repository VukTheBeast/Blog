(ns blog.models.formatteddata)

(def ? nil)
(def data
    "Keys of map represent the id of blogs! and value are ratings"
    {"Marko" {:5 4, :1 4 :2 4 :3 2 :4 3 :6 ?}
    "Aleksandar" {:6 0 :5 5 :1 4 :3 2 :2 ? :4 ?}
    "Jelena" {:5 4, :1 3 :2 2 :4 3 :3 ? :6 1}
    "Jovan" {:1 3 :2 3 :3 1 :4 4 :5 ? :6 3}})
