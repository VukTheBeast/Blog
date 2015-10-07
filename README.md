# Blog Site Developed in Clojure

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Database
In project Sqlite database is used.

**Database model:** <br />
3 tables: <br />
    <ol>
      <li>Blog</li>
      <li>Comment</li>
      <li>Ranking</li>
    </ol>

![alt tag](https://raw.githubusercontent.com/VukTheBeast/Blog/master/BlogModel.PNG)
## Description

In project for front end it is used  [Start Boostrap tamplate][2]. Frontend code is compleatly written in [Hiccup library][3] On home page you will see posts and view single post clicking on post title. You will be able to comment and rate the post from 1-5 by entering a username and value of rate or text for comment. To add a Post, you will need to go to Admin panel. When you click on menu admin , you will see login page. Entering validate credentials it will redirect you to admin panel.
    
[2]:http://startbootstrap.com/template-overviews/clean-blog    
[3]:http://weavejester.github.com/hiccup
## Summary

This is a  clojure projecs focused on making Blog site out of web development on clojure and build recommedation system based on Collaborative filtering. <br />

**Goal (in progress)** <br/>
 Show reccomedations blogs  based on collaborative filltering.


##Usefull links
[The Clojure Style Guide][4] , <br>
[Hiccup Tips][5]
[4]:https://github.com/bbatsov/clojure-style-guide
[5]:http://www.lispcast.com/hiccup-tips

## License
Copyright © 2015 Vuk Manic
