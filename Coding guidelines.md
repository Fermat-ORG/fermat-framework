


# Plug-in Databases

* The name must be a hash of the plug-in id.

# Return Types
 
* Any method that can not accomplish the task is intended to must throw an exception. Do not return null, zero or the like.


# Interface implementations

* The list of interface implementations must be in alphabetical order at the declaration level.
* The implementations themselves must be in the same order within the class with one comment announcing the start of the implementation.


# Class code structure

* All class variables must be declared at the top, separated in groups by a comment corresponding to each interface.
* Next the class constructor.
* Next section are the interface implementations also separated by a comment. Each method of an interface implementation must be in the order declared at the interface.
* Next section have private methods.


# File types

* xx


# Plug-ins files

* Each plug-in must store their files in a folder with a name resulting of the hashing of the plug-in id.
* If the plug-in must create files for certain objects, it should use a hash of their id as the file name. 
* What is mentioned above is done automatically by the PluginFileSystem.
