# WordCounter-Website
Current Project: Full-Stack Website (Unhosted, 03/2020 - Present)

## Contents
  * [Use](#Use)
  * [Purpose](#Purpose)
  * [Description](#Description)
  * [Technologies](#Technologies)
  * [Enhancements](#Enhancements)

## Use
  * Before running this program, go to the mail service layer and enter the appropriate username + password for Gmail account
  * In Gmail account, change security sessions to allow access from less-secure apps
  * Run in NetBeans
  * Go to localhost:8080 in your favorite browser

## Purpose  
Word Counter was my personal challenge to myself to work on my weaker areas concerning full-stack website development.
Client-side rendering, HTML modals, security (with encryption), and exception handling are all strongly emphasized in this project.

The idea stemmed from my studies of Zipf's Law (basically the idea that rank and frequency distribution have an inverse relationship, see https://en.wikipedia.org/wiki/Zipf%27s_law).
Since I write heavily, I thought it would be grand to analyze my own works and discover if my writings obeyed the Law. As it turns out, they do: For a given text, some words appear very frequently while most words appear only once or twice.

Word Counter demonstrates Zipf's Law by analyzing a user's input and displaying the results on scalar and logarithmic graphs (both rendered using chart.js).

## Description
 * Header is a row of pictures overlain with the website's title
 * Left side of webpage has a hamburger sidebar whose buttons change based on page and session status
 * Home page begins with a single input box, but after analyzing a text, the bottom half of the page becomes three tabs for viewing results
 * Account page lists all of a user's texts
 * Log in, sign up, change password, change email, delete account, save new text, etc. are all done using modals
 * Custom errors are generated then displayed to the user at the field which generated that error

## Technologies
Front-End Technologies:
  * HTML, CSS, Bootstrap
  * JavaScript (with jQuery)
  * AJAX (with JSON)
  * ChartJS

Back-End Technologies:
  * Java with Spring
  * Lombok
  * Java Persistence API (JPA)
  * Java Mail API

Database Technologies:
  * MySQL
  * MySQL Workbench

IDE and Text Editors:
  * NetBeans
  * Visual Studio Code (VS Code)
  
 ## Enhancements
 A future version of this site might include the following:
  * Scroll bar for table
  * Prettier logarithmic graph for small ranges of data
  * Character limit of more than 15,000 (should be able to use a BLOB in MySQL)
  * Prompt to log-in/create-account to save text when user is not logged in
  * Regular Expressions
 
 This website has known shortcomings because my goal was not to build a perfect website but to practice full-stack development and create a functional website.
 The following are some known shortcomings:
  * Imperfect bootstrapping
  * No administrator page
  * No way to contact website manager
  * No jUnit testing on the backend
  * Little to no JavaDoc comments
