(ns blog.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

;;database conneciton string
(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "db.sq3"})



(defn create-guestbook-table []
  (sql/with-connection
    db
    (sql/create-table
      :guestbook
      [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
      [:name "TEXT"]
      [:message "TEXT"])
    (sql/do-commands "CREATE INDEX timestamp_index ON guestbook (timestamp)")))



(defn create-blog-table
  "Table blogs"
  []
  (sql/with-connection
    db
     (sql/create-table
      :blog
      [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
      [:title "TEXT NOT NULL"]
      [:subtitle "TEXT NOT NULL"]
      [:postman "TEXT NOT NULL"]
      [:blog_content "TEXT NOT NULL"]
      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"])))


(defn create-comment-table []
  (sql/with-connection
    db
     (sql/create-table
      :comment
      [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
      [:comment_owner "TEXT"]
      [:message "TEXT"]
      [:related_blog :serial "references blog (id)"] ;;foreign key
      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"])))


(defn create-rating-table []
  (sql/with-connection
    db
    (sql/create-table
     :rating
     [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
     [:name "TEXT NOT NULL"]
     [:rat_val "INTIGER NOT NULL"]
     [:id_blog :serial "references blog (id)"] ;;foreign key
     )))


(defn read-blogs []
    (sql/with-connection
    db
    (sql/with-query-results res ;;res lazy
    ["SELECT * FROM blog ORDER BY timestamp DESC"]
    (doall res))))

(defn get-blog [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select * from blog where id = ?" id] (first res))))


(defn get-blog-title
  "Procedure used for reccomodation, for each key to fetch a blog title"
  [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select title from blog where id = ?" id] (first res))))


(defn get-comment [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select * from comment where related_blog =?"id] (doall res))))

(defn read-comments-all []
    (sql/with-connection
      db
      (sql/with-query-results res ;;res lazy
      ["SELECT * FROM comment ORDER BY timestamp DESC"]
      (doall res))))


(defn save-blog [title subtitle postman blog-content]
  (sql/with-connection
    db
      (sql/insert-values
      :blog
      [:title :subtitle :postman :blog_content :timestamp]
      [title subtitle postman blog-content (new java.util.Date)])))

(defn save-comment [comment_owner message id_blog]
  (sql/with-connection
    db
      (sql/insert-values
      :comment
      [:comment_owner :message :related_blog :timestamp]
      [comment_owner message id_blog  (new java.util.Date)])))


(defn read-guests []
    (sql/with-connection
    db
    (sql/with-query-results res ;;res lazy
    ["SELECT * FROM guestbook ORDER BY timestamp DESC"]
    (doall res)))) ;;doall becouse of res, to return all of record

(defn save-message [name message]
  (sql/with-connection
    db
      (sql/insert-values
      :guestbook
      [:name :message :timestamp]
      [name message (new java.util.Date)])))


(defn read-ratings []
   (sql/with-connection
    db
    (sql/with-query-results res ;;res lazy
    ["SELECT * FROM rating"]
    (doall res))))

(defn save-rating [name rat_val id_blog]
  (sql/with-connection
    db
      (sql/insert-values
      :rating
      [:name :rat_val :id_blog]
      [name rat_val id_blog])))


(defn get-rating [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select * from rating where id_blog =?"id] (doall res))))


(defn get-rating-avg
  "Get avarage ratings of blog"
  [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select ROUND(AVG(rat_val),2) as avgValue, COUNT(rat_val) as numPlp from rating where id_blog =?"id] (doall res))))

(defn get-rating-by-name [ime]
  (sql/with-connection db
    (sql/with-query-results
      res ["select id_blog, rat_val from rating where name =?"ime] (doall res))))

(defn get-rating-authors-name []
  (sql/with-connection db
    (sql/with-query-results
      res ["select distinct name from rating"] (doall res))))


(defn create-user-table []
  (sql/with-connection
    db
    (sql/create-table
      :users
      [:id "varchar(20) PRIMARY KEY"]
      [:pass "varchar(100)"])))


(defn add-user-record [user]
  (sql/with-connection db
    (sql/insert-record :users user)))

(defn get-user [id]
  (sql/with-connection db
    (sql/with-query-results
      res ["select * from users where id = ?" id] (first res))))
