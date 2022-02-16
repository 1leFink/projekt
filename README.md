# Programmieren-1-Projekt-HFT

## Task

Within the scope of our first project, we were tasked to write a basic software using the knowledge we gathered from the module Programmieren 1.
There were no further rules on what the software should be able to do except for a few things.  
1. It should be able to manage things utilizing the different concepts of OOP in Java.
2. We should use input and output streams to save and read data across sessions.

## What my project is about

I chose to design my software so it could be used to manage and display simple Information about costomers and a warehouse.

## How it works

Because we were not allowed to use any sort of GUI we were limited to using a console/command prompt.
Instead of using a rather slow and inefficient way to get user input by using menu navigation via number keys I choose to do something else:  

My Application works through the use of **commands** similar to how a shell works.
1. Different keywords are used for different commands (These keywords are rather long but make it easier for someone that never used a shell before)
2. **Parameters** after Keywords (commands) can be used to extend the functionality of the command or to specify what element the command should refer to.
3. **Advanced Parameters** begin with a `-` and result in special behavior for some commands.
4. The command `help` shows all the possible commands, parameters, and use cases.

## Examples

`help`

![help](https://user-images.githubusercontent.com/62705365/154351776-3528e910-6a6f-40a0-8837-f51aea3513a9.png)

`addKunde`

![addKunde](https://user-images.githubusercontent.com/62705365/154352425-37319aff-eda2-4eb3-a483-b9f5c085a4dd.png)

`rmKunde`

![rmKunde](https://user-images.githubusercontent.com/62705365/154352452-25e42a16-6000-45cc-bfb0-632efa3ddf1b.png)

`listKunden`

![listKunden](https://user-images.githubusercontent.com/62705365/154352488-63ce7b83-a3e8-4c19-85ff-1d41c6ecdfa2.png)

`listLager`

![listLager](https://user-images.githubusercontent.com/62705365/154352537-f4fc6000-fd0f-453c-9b95-a3562fb43595.png)

## UML Diagram

![UML](https://user-images.githubusercontent.com/62705365/154353019-7c61c2cc-b587-4702-97f3-ee4b73b4408c.png)

note: The above diagram does not include all relationships between classes and should only be used as an overview.

