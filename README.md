# Programmieren-1-Projekt-HFT

## Task

Within the scope of our first project we were tasked to write a basic software using the knowledge we gathered from the module Programmieren 1.
There was no further rules on what the software should be able to do except for a few things.  
1. It should be a able to manage things utilizing the different concepts of OOP in Java.
2. We should use input- and outputstreams to save and read data across sessions.

## What my project is about

I chose to design my software so it could be used to manage and display simple Information about Costumers and a warehouse.

## How it works

Because we were not allowed to use any sort of GUI we were limited to using a console / command prompt.
Instead of using a rather slow and inefficent way to get user input by using a menu navigation via number keys I choose to do something else:  

My Application works through the use of **commands** similar to how a shell works.
1. Different keywords are used for different commands (These keywords are rather long but make it easier for someone that never used a shell before)
2. **Parameters** after Keywords (commands) can be used to to extend the functionality of the command or to specify what element the command should refer to.
3. **Advanced Parameters** begin with a `-` and result in special behaviour for some commands.
4. The command `help` shows all the possible commands, parameters and use cases.

## Examples

`help`:

