clipapp
=======
[![Build Status](https://travis-ci.org/pzn/clipapp.svg?branch=master)](https://travis-ci.org/pzn/clipapp)

what is it?
===========
A simple REST webapp, tinyurl-alike, built with Spring Boot.

live demo
=========
http://clipapp.herokuapp.com
- http://clipapp.herokuapp.com/?u=LONG_URL: generates a tiny url
- http://clipapp.herokuapp.com/TINY_URL: resolves the original url

launch it locally?
==================
## prerequisites
- jdk8
- maven
- postgresql 9.4.x, with database *clipapp* created

## how to
- git clone this repository
- mvn spring-boot:run -Dspring.profiles.active=database

improvements
============
- find a way to disable hibernate, when running in "simple mode" (in-memory map, Spring Profile "simple")
- merge 'hazelcast' branch
