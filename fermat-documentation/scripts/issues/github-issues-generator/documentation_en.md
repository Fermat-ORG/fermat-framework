# GitHub Issues Generator
This script automatically generates all the issues necessary for the implementation of a fermat plug-in.

## INSTALL AND RUN

 To achieve this objective, this script uses python-github3, which is used to handle the github api from python.
 To install python-github3 in GNU/Linux you may need to install the Python Package Index, please use this command:
 
```shell
sudo apt-get install python-pip
```
 
 Now you need to install the github api python library:
 
 ```shell
 sudo pip install pygithub3
 ```
 
	
To run this script in the ubuntu shell, please, run this command:
 
 ```shell
 python $SCRIPT_PATH/issuesGenerator.py
 ```

## CONFIGURATION

Please, configure your script to generate all the issues following the next steps:

	1) Set the platform var: this represents the plug-in platform.
	2) Set the layer var: this represents the plug-in layer.
	3) Set the pluginName var: this represents the plug-in name, please use capital characters in the first character on every word.
	4) Set the githubLogin var: this represents your login user in github.
	5) Set the githubPassword var: this represents your login password in github.
	6) Set the repository var: this represents the target repository.
	7) Set the teamLeaderGithubUser var: this represent the responsible or team leader that approves your pull-requests.
	8) Adjust the public interfaces list: you can add any element according the plug-in structure.
	9) Adjust the internal clases list: you can add any element according the plug-in structure.
	10) Adjust the event handlers list: you can add any element according the plug-in structure.

### CHANGELOG
* 1.0.2: 	
    * Fixes the issue publication in repositories that not owned by the developer.
    * The developer message for a team leader is not longer print if the githubLogin is equal to teamLeaderGithubUser
* 1.0.3:
    * If you are a Team Leader you can do an autoassignment.
* 1.0.4:
	* Added Public Interfaces, Internal Structure and Event Handler issue.