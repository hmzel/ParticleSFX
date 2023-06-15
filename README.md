<h1 align="center">
  <img width=250 height=250 src="https://github.com/hmzel/ParticleSFX/assets/96555008/696b0c03-9350-4127-b678-b5101b2a4886">
  
  ParticleSFX
</h1>

<p align="center">
  <b>A Spigot library for making special effects out of particles</b><br><br>
  <a href="#features">Features</a> •
  <a href="#gettingstarted">Getting Started</a> •
  <a href="#usage">Usage</a> •
  <a href="#showcases">Showcases</a>
</p>
<br>

ParticleSFX is a particle library for Spigot that allows you to display particles and make shapes out of them. Designed to be fast, flexible, user-friendly, and not resource-intensive, this library allows 
even novice plugin developers to do things that most servers can only dream of.

## Features
- Supports every version and particle from 1.8.8 to latest.
- Allows you to modify all applicable particle data, such as motion, color, material, size, even data specific to one particle like dust_transition's transition color or sculk charge's angle.
- Shapes that you can rotate, morph, move, and modify in almost any way you would want. You can even display images, gifs, and text.

## Getting Started
Add this to your pom.xml, replacing `version` with the version you want to use:
```xml
<dependency>
  <groupId>hm.zelha</groupId>
  <artifactId>particlesfx</artifactId>
  <version>version</version>
</dependency>
```
ParticleSFX versions support every minecraft version between their version number and the next ParticleSFX version.
for instance, version 1.16.2 supports minecraft 1.16.2 and 1.16.3, but not 1.16.4. <br>
For a list of every version, look [here](https://github.com/hmzel/ParticleSFX/packages/1878252/versions)

Once the dependency is added, be sure to download the javadocs and sources by clicking these buttons in intellij:
![image](https://github.com/hmzel/ParticleSFX/assets/96555008/9267ba98-2c12-4a5a-87e9-24950c4fc770)
![image](https://github.com/hmzel/ParticleSFX/assets/96555008/2a97fa51-e346-40b0-bb35-68fa4a9f43ab) <br>
or by running `mvn dependency:sources dependency:resolve -Dclassifier=javadoc` in your project folder.

## Usage
[Shapes](#shapes) • [Simple Particles](#simpleparticles) • [Travelling Particles](#travellingparticles) • [Colorable Particles](#colorableparticles) • [Material Particles](#materialparticles) • [Special Particles](#specialparticles)
### Shapes
Make sure to add `ParticleSFX.setPlugin(this);` to your `onEnable()` method before using any shapes. <br>
For some complicated code examples of how to use the shape classes, check out the [ParticleSFX class](https://github.com/hmzel/ParticleSFX/blob/master/src/main/java/hm/zelha/particlesfx/util/ParticleSFX.java)

All shape classes have an array of rotation methods, from rotate() to face() to even axis rotation. There's move() and scale() methods too.

By default, shapes display particles to all players within their world, but you can change that by using Shape#addPlayer().

You can modify the tick delay between shapes running display() using Shape#setDelay().

You can set the amount of particles the shape displays when display() is called using Shape#setParticlesPerDisplay(). Assuming the given int is less than ParticleFrequency, display() will display the given amount
of particles and stop, and continue from there the next time display() is called, and so on, until it reaches the end and restarts. <br>
You can also set the amount of particles the shape thinks it's already displayed using Shape#setDisplayPosition().

You can add secondary particles to display after x amount of other particles using Shape#addParticle().

Shapes have a system similar to Consumer<>s which lets you run your own code during the shape's display method using Shape#addMechanic(), for more info look at the javadocs 
[here](https://github.com/hmzel/ParticleSFX/blob/master/src/main/java/hm/zelha/particlesfx/shapers/parents/Shape.java#L80) and [here](https://github.com/hmzel/ParticleSFX/blob/master/src/main/java/hm/zelha/particlesfx/util/ShapeDisplayMechanic.java#L10).

Note: The amount of particles some shapes display can be slightly off of ParticleFrequency. Some of the math involved just doesn't let you limit the particles to certain numbers.

You can also freely modify locations that have been added to shapes, nothing will break.

<br><b>ParticleLine</b><br>
Simple lines connecting 2 or more locations.

<br><b>ParticleLineCurved</b><br>
Same as ParticleLine except you can add curves to it. <br>
Curves have height, length, apexPosition, pitch, yaw, and roll. <br>
the apex position is the point along the length of the curve with the most height. <br>
You'll have to set the pitch, yaw, and roll yourself based on what you're doing with this class.

<br><b>ParticleCircle</b><br>
A simple circle. You can modify the X/Z radius to make it an oval, and/or use ParticleCircle#setLimit() to make it a half-circle.

<br><b>ParticleCircleFilled</b><br>
Same as ParticleCircle, except it uses the [Sunflower Seed Arrangement](https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42) to fill the circle.

<br><b>ParticleSphere</b><br>
A simple sphere. Uses the [Sunflower Seed Arrangement](https://medium.com/@vagnerseibert/distributing-points-on-a-sphere-6b593cc05b42) to distribute particles across its surface. <br>
You can modify the X/Y/Z radius to make it an ellipsoid, and/or use ParticleSphere#setLimit() to make it a half-sphere.

<br><b>ParticleSphereCSA</b><br>
Same as ParticleSphere, except it uses circumference and surface area to distribute particles across the shape. <br>
This makes it easier to add secondary particles and do things with mechanics, as the sunflower seed arrangement places particles pretty chaotically.

<br><b>ParticlePolygon</b><br>
This class can generate 2D squares, 3D cubes, hexagons, pyramids, etc. depending on what params you give it.

<br><b>ParticlePolygonFilled</b><br>
Same as ParticlePolygon, except it randomly distributes particles across the polgon's surface.

<br><b>ParticleCylinder</b><br>
Similar to ParticleLine, ParticleCylinder connects 2 or more circles. <br>
Each circle can have its own location, X/Z radius, pitch, yaw, and roll. <br>
If circleFrequency is higher than the amount of circle objects, it will automatically generate the missing circles.

<br><b>ParticleSpiral</b><br>
This class generates a spiral between 2 or more circles. You can increase the number of spirals and change how much spin the spiral has. <br>
1 Spin means the spiral rotates 360 degrees across its entire length.

<br><b>ParticleImage</b><br>
This class can display jpgs, pngs, gifs, and probably every other type of image, and it can get said images from a file or from a link. <br>
You can ignore colors using ParticleImage#addIgnoredColor() and set how close the image's pixel's RGB has to be to ignored colors in order to be ignored using ParticleImage#setFuzz(). <br>
You can set how many times ParticleImage has to display before going to the next frame using ParticleImage#setFrameDelay().

<br><b>ParticleText</b><br>
This class displays text. it can have different fonts, be centered or not centered, and even be inverted so the particles are displayed around the text. <br>
Every new string object added to ParticleText is displayed on a separate line.

<br><b>ParticleFluid</b><br>
This class gives particles rudimentary fluid mechanics. You can set the gravity, repulsion, attraction, and how close particles have to be to be repulsed. <br>
ParticleFluid particles even have block and entity collision!

<br><b>ParticleShapeCompound</b><br>
ParticleShapeCompound basically allows you to combine different shapes and modify them as if they were the same object. <br>
It also has a naming system, so you can add shapes to it with a string and get them with the same string.

---

### Simple Particles
ParticleSFX has a class for every particle, and in most cases you can set all relevant data in the particle's constructor.

<b>Example:</b>
```java
new ParticleHeart(/*offset X/Y/Z*/ 2, 2, 2, /*count*/ 1).display(location);
```
All classes in ParticleSFX also have a lot of constructors to make your life easier, it can be as simple as:
```java
new ParticleCrit().display(location);
```
Note: You should store particle objects in variables instead of making a new object whenever you want to display a particle.

All particles have offsetX/Y/Z, count, radius, and speed variables. <br>
All variables that aren't assigned via a constructor have set methods that return the modified object. <br>
For a list of every particle, go [here](https://github.com/hmzel/ParticleSFX/tree/master/src/main/java/hm/zelha/particlesfx/particles) on the [version branch](https://github.com/hmzel/ParticleSFX/branches) that your project is using.

---

### Travelling Particles
Particles that extend TravellingParticle can have a velocity vector or a location for the particle to go to.

<b>Examples:</b>
```java
new ParticleCrit(new Vector(0, 10, 0)).display(location);
```
```java
new ParticleFlame(new Location(world, 0, 100, 0)).display(location);
```
<br>
In most cases, a velocity of 0, 10, 0, means that the particle will travel 10 blocks upwards. <br>
If both velocity and location to go are null, then the particles will go in a random direction based on the speed variable. Otherwise, speed is unused. <br>
Travelling particles also have methods that allow you to display the particle with different velocity or location to go wthout modifying the object's variables.

---

### Colorable Particles
Particles that extend ColorableParticle can have a color, or be randomly colored if the color is null or not provided.

<b>Example:</b>
```java
new ParticleDustColored(Color.WHITE).display(location);
```
<br>
ColorableParticles also have a brightness variable that goes from 0-100.

---

### Material Particles
Particles that implement MaterialParticle take a Material, or MaterialData pre-1.13.

<b>Examples:</b>
```java
//post-1.13
new ParticleBlockBreak(Material.DIAMOND_BLOCK).display(location);
```
```java
//pre-1.13
new ParticleBlockBreak(new MaterialData(Material.DIAMOND_BLOCK)).display(location);
```

---

### Special Particles
There are a lot of special particles:
- ParticleDustColored has a pureColor variable to eliminate the random color differences when it's displayed, and in the latest versions it has a size variable.
- ParticleDustMulticolored has everything ParticleDustColored has, plus a transition color variable.
- ParticleExplosion and ParticleSweepAttack have size variables.
- ParticleVibration has an entity variable, which will make the particle track the given entity client-side.
- ParticleSculkCharge has a roll variable which determines the angle of the particle.
- ParticleShriek has a delay variable.
- ParticlePortal has an inverse variable Post-1.16.1 which determines whether or not it uses PORTAL or REVERSE_PORTAL.
- ParticleNote has its own special color system Pre-1.9.4.
- ParticleNull displays nothing and is only meant for special use in shape classes.

## Showcases
https://github.com/hmzel/ParticleSFX/assets/96555008/583b34ad-c56e-43ce-ad2b-283e3a477011

# [beating the eye of cthulhu in minecraft](https://www.youtube.com/watch?v=T1b8xYs3OHU) <br>
![2022-11-07_17 21 04](https://github.com/hmzel/ParticleSFX/assets/96555008/51040240-866a-4ffe-a2cd-66d5430ca905)
![2022-12-02_16 35 46](https://github.com/hmzel/ParticleSFX/assets/96555008/300cb844-3c8c-44dc-b926-7b7df695d5d6)
![2022-12-20_17 36 15](https://github.com/hmzel/ParticleSFX/assets/96555008/cb730d22-2043-454e-9016-2f4d03a48200)
![2022-12-20_17 55 24](https://github.com/hmzel/ParticleSFX/assets/96555008/742afdf7-4650-4fa5-83e1-f6f1060b7a97)
![2022-12-22_23 42 16](https://github.com/hmzel/ParticleSFX/assets/96555008/1309723f-11a7-4f03-91a7-a8995bb754bc)
![2023-06-05_05 01 30](https://github.com/hmzel/ParticleSFX/assets/96555008/5a0ba1ec-c937-4a5c-b6f3-b1bcc61ad259)
![2023-06-04_05 42 03](https://github.com/hmzel/ParticleSFX/assets/96555008/a8eb64c6-2888-4855-bb90-5c048c1db2ab)
![2022-11-21_20 29 03](https://github.com/hmzel/ParticleSFX/assets/96555008/d04a2610-db3b-4603-a64c-bdbab25bc921)
![2023-05-28_20 27 53](https://github.com/hmzel/ParticleSFX/assets/96555008/3dacbffa-adb8-4ec2-a851-6281f71d4020)
