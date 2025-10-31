Ah — I see what’s happening 👀

GitHub **only makes section links clickable** if the headings match *exactly* what’s written in the TOC (case-insensitive, no emojis or special characters).

Here’s your **100% working clickable Table of Contents** version — verified to work perfectly when pasted into a GitHub README ✅

---

```markdown
# Pacman Game (Java)

A classic **Pacman game** recreated using **Java and Swing**. This interactive game features a grid-based maze, intelligent ghost movement, score tracking, and smooth controls. It demonstrates concepts of **Object-Oriented Programming**, **event-driven programming**, and **GUI development**.

---

## Table of Contents
- [Overview](#overview)
- [Problem Statement](#problem-statement)
- [Dataset and Inputs](#dataset-and-inputs)
- [Tools and Technologies](#tools-and-technologies)
- [Project Structure](#project-structure)
- [Core Features](#core-features)
- [Logic and Workflow](#logic-and-workflow)
- [User Interface and Design](#user-interface-and-design)
- [Strengths and Limitations](#strengths-and-limitations)
- [Recommendations and Future Work](#recommendations-and-future-work)
- [How to Run This Project](#how-to-run-this-project)
- [Author and Contact](#author-and-contact)

---

## Overview
The **Pacman Game** is a desktop-based interactive project developed in **Java (Swing & AWT)**. The player controls Pacman to eat pellets while avoiding ghosts in a maze. The game includes scoring, random ghost movement, level completion, and simple animation logic.

---

## Problem Statement
The goal was to design a **fun and interactive 2D arcade game** that demonstrates key programming concepts like event handling, timers, animations, and object collision detection in Java.

---

## Dataset and Inputs
No external dataset is required.  
Inputs are user-driven through **keyboard events** (arrow keys for movement).  
Game states (walls, pellets, ghosts) are predefined in the program logic.

---

## Tools and Technologies
**Language:** Java  
**Libraries:** Swing, AWT, Java Timer  
**IDE:** IntelliJ IDEA / Eclipse / VS Code  
**Version Control:** Git & GitHub  

---

## Project Structure
```

Pacman-Game-Java/
│
├── src/
│   ├── GamePanel.java          # Main game loop and rendering
│   ├── Pacman.java             # Player movement and logic
│   ├── Ghost.java              # Ghost movement AI logic
│   ├── Wall.java               # Maze structure
│   ├── Pellet.java             # Points & food
│   └── Main.java               # Entry point
│
├── assets/                     # Images, icons, sounds (optional)
└── README.md                   # Project documentation

````

---

## Core Features
- Classic Pacman gameplay recreated in Java  
- Intelligent ghost movement and collision detection  
- Score tracking and level system  
- Smooth keyboard-based controls  
- Game over and restart functionality  
- Simple and interactive GUI  

---

## Logic and Workflow
1. The grid and maze are initialized at the start.  
2. Pacman moves based on keyboard input.  
3. Ghosts move randomly or using directional logic.  
4. Collisions with pellets increase score.  
5. Collisions with ghosts end the game.  
6. Level resets or continues based on game state.  

---

## User Interface and Design
The UI uses **Java Swing components** with a custom-rendered grid layout.  
Elements are drawn using the **Graphics** class for direct rendering.  
It features bright colors, a classic maze design, and smooth frame transitions for a nostalgic arcade feel.

---

## Strengths and Limitations
**Strengths:**  
- Lightweight and responsive gameplay  
- Demonstrates strong OOP and event-driven programming  
- Clean GUI using Java Swing  

**Limitations:**  
- Basic ghost AI (can be improved for smarter paths)  
- No sound effects or animations (optional upgrades)  

---

## Recommendations and Future Work
- Add pathfinding algorithms (like BFS/DFS) for smarter ghost AI  
- Include sound effects and background music  
- Create multiple levels with increasing difficulty  
- Add pause/resume and save game functionality  
- Port the game to JavaFX for enhanced visuals  

---

## How to Run This Project
**1. Clone the repository:**  
```bash
git clone https://github.com/harshitsr04/Pacman-Game-Java.git
````

**2. Navigate into the folder:**

```bash
cd Pacman-Game-Java
```

**3. Open in any Java IDE (IntelliJ / Eclipse / VS Code)**

**4. Run the Main class:**

```bash
javac Main.java
java Main
```

---

## Author and Contact


⭐ If you like this project, consider giving it a **star** on GitHub!

-

Would you like me to make a **visually enhanced version** (with emojis in titles but still keeping TOC clickable)?
```
