## Starter Project Template For LibKTX

[Libktx](https://libktx.github.io/) is a library which is ideal for people who want to use libgdx with kotlin. While interacting with gdx libraries 
directly with kotlin is possible, ktx provides libraries which wrap gdx and make using it more kotlin-centric and idiomatic.

Before you can start getting into the code, however, you need to get your dependencies set up, make sure the ide is happy, try to figure out 
why gradle is running into errors... well - that's what this project is for - to hopfully give you a starting place where you don't have to 
edit all the build files.

It's a minimal starting point to extend kdx, you should be able to open it in android studio and begin messing around without too much fuss.

![template.gif](.github/template.gif?raw=true)

I use this project as a starter for a new project and I also like to replace 'core/src' with the source code from another project. You might 
only need to change the desktop/src/com/central/DesktopLauncher.kt file so it starts the main application class of whatever project you 
dropped in there.

# Quick Start

Your game goes in the 'core' module inside of 'core/src/com/central'. Ktx has a class called 'KtxGame', which is the whole application, and 
KtxScreen, which is like a scene. This example only has one screen, which is called 'Game', which is inside the 'core/src/com/central/screens' folder. This is where you can load images, sounds, etc. Libgdx turns off Java garbage collection for your assets - so for all of the types listed on this page with [libgdx documentation on memory management](https://github.com/libgdx/libgdx/wiki/Memory-management), you should call their 'dispose()' method inside of the screen's 'dispose' function. There are tools, like AssetManager, which help with this, but you probably don't need to think about that until your game gets a little bigger. 

I'm anticipating that you're going to need a camera and stage, and those are available in the 'App' class, which is exposed to 'Game' as an 
argument - take a look at [the scene2d documentation](https://github.com/libgdx/libgdx/wiki/Scene2d) and add some images, sprites etc to the 
game. Don't forget to add stg.act(delta) and stg.draw() to the render function after adding Actors to the stage.

For examples of what I'm talking about, see these examples which build off of this template.

[https://github.com/jojomickymack/on_screen_gamepad01](https://github.com/jojomickymack/on_screen_gamepad01)  

[https://github.com/jojomickymack/superkrab](https://github.com/jojomickymack/superkrab)  

# Run The App

Run the desktop launcher - you can either right click on 'desktop/src/com/central/DesktopLauncher.kt' and run it from Android Studio, or use the gradle task (click on the gradle tab on the far right to reveal gradle tasks) and expand the 'desktop::application' groups. The 'run' task, which is added by the 'application plugin' in gradle, will also run the desktop launcher.

# Kotlin Not Required

This project will look for Java or Kotlin files inside of 'core/src' - if you remove the kotlin files and replace them with Java files, it'll work the same way if you load the Java code in the launchers (maybe you are following a Java tutorial or something).

# Executable Jar

Do you want to compile an executable jar to distribute the desktop version of your game? I added [John Rengelman's shadowjar plugin](https://github.com/johnrengelman/shadow) to the desktop module, which will create a fatjar (containing all dependencies in one). When you run the 'desktop::shadow::shadowJar' task, it will create your fatjar in 'desktop/build/libs'. You should be able to double click on the jar file to launch your game.

# Changing The Package Name

You probably want to change the project package name eventually, which is com.central (really generic)

- in the core module
  - create a new package path and move all of the classes into it - Android Studio will update the package paths in the files
  - change the package in the android and desktop modules to match the core package path - that keeps things consistent and also makes it so you don't have to import anything in the Launcher classes (they share the same namespace)
- in the desktop module
  - in the build.gradle file, change the mainClassName so it matches your new package path - that's what allows the 'application::run' task to work
- in the android module
  - in the build.gradle file, change the application id so it isn't com.central anymore
  - in the AndroidManifest.xml, change the 'package name' so it matches the new package path
  - in android/res/strings.xml, change the name of the app from 'Ktx Template' to whatever you want.
- in the root of the project's build.gradle
  - change the appName from ktx-template to whatever you want.

If you run into issues immediately after doing that...

Sometimes Android will get messed up if you install an app, change the id or something else about it, and attempt to debug it again - it'll say that the app couldn't be uninstalled. The way I've solved this in the past is by manually uninstalling the app from the device (you might have to go into the settings and find the app under apps there), and run the 'android::build::cleanBuildCache' task from Android Studio.

If you're really stuck, close Android Studio, delete the .idea, .gradle, build directories and any .iml files (don't forget the build directory inside of each module) - then just open Android Studio again and tell it to open the project directory again. It will replace what you just deleted and the next build will be from scratch - sometimes that can resolve problems. That's also what I do when making a new version of a project - just copy the folder, delete those folders, change the name of the folder to what you want Android Studio to think the project is called, and open the folder in Android Studio.

# Additional Notes  

- android studio may ask you if you want to update 'android gradle plugin', just click on 'don't remind me again for this 
project' since it can screw up the build files

- if you run into build problems, remember you can close Android Studio and delete the .idea, build directories, gradle, and all the .iml 
files - then open the project again in Android Studio and all those files will be recreated new

I used the gradle wrapper for this project so you don't necessarily need to download gradle to your computer - you probably will want to do 
that eventually though - all you need to do is download the binary only gradle version you want (this project uses gradle 5.1). Android 
Studio has a setting under 'build, execution, and deployment' where you can set gradle's location. You don't need the gradle 
directory, or the gradlew, gradlew.bat files when you do it that way.

[https://gradle.org/release](https://gradle.org/release)

# Some Tutorials

I did a few write-ups on my blog descibing how to integrate tiled maps with your game.

[https://jojomickymack.gitlab.io/reverie/post/libgdx_kotlin](https://jojomickymack.gitlab.io/reverie/post/libgdx_kotlin)  
[https://jojomickymack.gitlab.io/reverie/post/libktx_platformer](https://jojomickymack.gitlab.io/reverie/post/libktx_platformer)  

here's one describing some how to get started with entity component systems - a concept that can help when you have a lot of game objects to manage.

[https://jojomickymack.gitlab.io/reverie/post/ashley_ecs](https://jojomickymack.gitlab.io/reverie/post/ashley_ecs)

# What Else?

If you take a bunch of java code and paste it into a kotlin file, Android Studio will ask if you want it to have it turn the java code into kotlin - this is a great way to take some libgdx examples in java and try it out in kotlin. You can take some Java or Kotlin example code and put it into the 'core/src' directory and change the launchers to they bootstrap the Java code instead of 'com.central.App'.

If you're looking for general libgdx tutorials, you might want to [start with this](https://libgdx.info/basic_image) if you haven't used scene2d before.

I hope this project helps you have fun and makes it easier to get up and running!
