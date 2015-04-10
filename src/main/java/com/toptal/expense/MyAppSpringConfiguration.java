/************************************************************************
 * FileName - MyAppSpringConfiguration.java
 * 
 * $Revision: 12 $
 * $Author: Nicolas $
 * $Date: Apr 9, 2015 $
 ************************************************************************/
package com.toptal.expense;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
Main Spring Configuration
 */
@Configuration
@ComponentScan(basePackageClasses = MyAppSpringConfiguration.class)
public class MyAppSpringConfiguration {
}