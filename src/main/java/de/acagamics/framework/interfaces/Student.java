package de.acagamics.framework.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Student {
/**
 * Returns the name of the student of this player controller.
 * @return Name of the student
 */
   String author();

/**
 * The Matrikelnummer of the student of this controller.
 * @return Matrikelnummer of the student.
 */
   long matrikelnummer();


   /**
    * An optional name of the bot for the competition else the class name will be used.
    * The class name may be altered.
    * @return optinal name of bot.
    */
   String name() default "";
}
