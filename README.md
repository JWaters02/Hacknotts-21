# Hacknotts-21 Project: Drag'on' Drop
## Inspiration
Originally we wanted to create a joke program that would generate Python from Java, that would then generate code for another language, then back to Python, forming a loop. Then we realised it was a terrible idea - but it inspired our current idea. Josh is also working for a charity as part of his university coursework that wants Scratch and Python content to teach to kids, and realised that a program like this would actually aid the project greatly. 

The name came about because people kept asking what we were doing, and we ended up explaining it like
> Imagine Scratch's drag 'n' drop system to create code "blocks" that generate actual code rather than straight to a program.
People kept mishearing "drag 'n' drop" as "dragon drop" so a merge of them kinda stuck :) 

## What it does
It takes its own form of the classic Scratch drag and drop of "nodes" or "blocks" into a panel that then compiles into real code in real-time. There are a few basic building blocks for programs, with categories covering variables, conditions, operators, loops and functions. Supported languages currently are Java and Python, but the backend is generic enough to make it easy to add support for new languages.

## How we built it
Aided by junk food, we built the program with a few successes (especially in the last 10 minutes when we actually got the code compiling to work) and a lot of pain (thanks JSwing!)

## Challenges we ran into
JSwing being annoying. More specifically, dragging and dropping our blocks into the function panels. This was because JSwing does not implement draggable transfer handle by default onto JPanels... which is the only component type that we could use to make our idea work. In fact, a lot of Java being really annoying moments, like Java generics.

## Accomplishments that we're proud of
Implementing our own drag and dropping support for JPanels, graph structure, staying awake (except Sammy), and the serialization/deserialization to save/load the code blocks in the form of JSON.

## What we learned
That Java is still bad, that JSwing can be really annoying, and that GitHub Copilot is seriously underrated.

## What could be improved (but probably won't be)
There needs to be work to polish it via bug fixing (of which there are still plenty) and artistic touchups, add support for a couple new languages (e.g. LOLCODE), and maybe add more node types.

## What the program looks like
![](https://github.com/JWaters02/Hacknotts-21/blob/master/Python%20Compile%20Example.jpg)
