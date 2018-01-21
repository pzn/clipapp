clipapp
=======
[![Build Status](https://travis-ci.org/pzn/clipapp.svg?branch=master)](https://travis-ci.org/pzn/clipapp)

what is it?
===========
A simple webapp, tinyurl-alike, built with Spring Boot.

live demo
=========
# Web UI
http://clipapp.herokuapp.com

# GET
- http://clipapp.herokuapp.com/{TINY_URL}: redirects to the original URL if existing, otherwise redirects to the web ui.

# POST
- http://clipapp.herokuapp.com/{TINY_URL}: outputs the original URL if existing, otherwise throws an error.
```json
{
  "url": "https://www.google.ca"
}
```

- http://clipapp.herokuapp.com/?u={LONG_URL}: outputs a tiny URL. If already available in the system, it gives the already associated tiny URL. If long URL malformed, throws an error.
```json
{
  "value": "jfcnsie"
}
```

launch it locally?
==================
## prerequisites
- jdk8
- maven
- postgresql 10.x, with database *clipapp* created

## how to
- git clone this repository
- mvn spring-boot:run -Dspring.profiles.active=database

improvements
============
- merge 'hazelcast' branch
