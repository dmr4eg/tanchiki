package net;

/*
Client            Server
-1.----------------------
  <----- SUBMIT ------
  --- NAME|<name> --->
  <---- REJECTED -----
-2.----------------------
  <----- SUBMIT ------
  --- NAME|<name> --->
  <---- ACCEPTED -----
  - BROADCAST|<msg> ->
  <--- CHAT|<msg> ----
  ...
  ------- QUIT ------>
*/
public enum Protocol {
    // server origin:
    SUBMIT,
    ACCEPTED,
    REJECTED,
    CHAT,
    // client origin:
    NAME,
    BROADCAST,
    QUIT
}
