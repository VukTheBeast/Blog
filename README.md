# Item-based collobarative filltering using Slope One algorithm in Clojure

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

If the command is successfully executed, the ring server should be up and running on port 3000 (by default) The app can then be viewed on http://localhost:3000/

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

<b>Reccomodation system</b> is based on collobarative filltering. Collaborative filtering methods are based on collecting data about a given user's behavior, activities, or preferences and using this information to recommend items to users. In this project, it used Slope One Algorithm. The Slope One algorithm is one of the simplest forms of item-based collaborative filtering,
which is essentially a collaborative filtering technique in which the users explicitly rate
each item they like (for more information, refer to a [wiki][6]). Generally, item-based collaborative filtering
techniques will use the user's ratings and past behavior of users to estimate a simple
regression model for each user. Thus, we estimate a function fu (x) = ax + b for all
users u in the system.
[6]:https://en.wikipedia.org/wiki/Slope_One    
[2]:http://startbootstrap.com/template-overviews/clean-blog    
[3]:http://weavejester.github.com/hiccup
## Summary

This is a  clojure projecs focused on making Blog site out of web development on clojure and build recommedation system based on Collaborative filtering. <br />

**Goal** <br/>
 Show reccomedations blogs  based on collaborative filltering.


##Usefull links
[The Clojure Style Guide][4] , <br>
[Hiccup Tips][5]
[4]:https://github.com/bbatsov/clojure-style-guide
[5]:http://www.lispcast.com/hiccup-tips

##Books
[Clojure for Machine learning][7]<br />
[Programming Collective Intelligence][8]
[7]:http://www.amazon.com/Clojure-Machine-Learning-Akhil-Wali/dp/1783284358/ref=sr_1_1?s=books&ie=UTF8&qid=1444773010&sr=1-1&keywords=Clojure+for+Machine+learning 
[8]:http://www.amazon.com/Programming-Collective-Intelligence-Applications-Paperback/dp/B00E82JV3S/ref=sr_1_8?s=books&ie=UTF8&qid=1444773034&sr=1-8&keywords=Programming+Collective+Intelligence

## License
Copyright © 2015 Vuk Manic
