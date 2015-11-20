#This script automatically generates all the issues necessary for the implementation of a fermat plug-in.
######
#INSTALL AND RUN
######
#To achieve this objective, this script uses python-github3, which is used to handle the github api from python.
#To install python-github3 in GNU/Linux you may need to install the Python Package Index, please use this command:
#	sudo apt-get install python-pip
#Now you need to install the github api python library:
#	pip install pygithub3
#To run this script in the ubuntu shell, please, run this command:
#	python $SCRIPT_PATH/issuesGenerator.py
######
#CONFIGURATION
######
#Please, configure your script to generate all the issues following the next steps:
#	1) Set the platform var: this represents the plug-in platform.
#	2) Set the layer var: this represents the plug-in layer.
#	3) Set the pluginName var: this represents the plug-in name, please use capital characters in the first character on every word.
#	4) Set the githubLogin var: this represents your login user in github.
#	5) Set the githubPassword var: this represents your login password in github.
#	6) Set the repository var: this represents the target repository.
#	7) Set the teamLeaderGithubUser var: this represent the responsible or team leader that approves your pull-requests.
#	8) Adjust the public interfaces list: you can add any element according the plug-in structure.
#	9) Adjust the internal clases list: you can add any element according the plug-in structure.
#	10) Adjust the event handlers list: you can add any element according the plug-in structure.
#
#This script was created by Manuel Perez (darkpriestrelative@gmail.com) on 10/11/2015
######
#CHANGELOG	
######
#	1.0.2: 	- Fixes the issue publication in repositories that not owned by the developer
#		- The developer message for a team leader is not longer print if the githubLogin is equal to teamLeaderGithubUser
#   1.0.3:	- If you are a Team Leader you can do an autoassignment.
#   1.0.4:  - Added Public Interfaces, Internal Structure and Event Handler issue.
 
from pygithub3 import Github

#Environment vars.
platform='CBP'
layer='Business Transaction'
pluginName="Bank Money Restock"
githubLogin='login'
githubPassword='pass'
repository='fermat'
teamLeaderGithubUser="teamLeader"

#Please, not modify this line.
pluginClassName=pluginName.replace(' ','')

#You can modify this lists to adjust to the plug-in structure.
#This List contains all the public interfaces to develop in the plug-in.
publicInterfaces=[pluginClassName, pluginClassName+"Manager", "DealsWith"+pluginClassName]

#This list contains all the internal clases to develop in the plugin.
internalStructureClasses=[pluginClassName+"Manager", pluginClassName+"Class"]

#This list contains all the event handlers to develop in the plugin.
eventHandlers=["EventHandler1","EventHandler2"]

#Please, not modify this plugin after this point
if(teamLeaderGithubUser!=githubLogin):
	teamLeaderMessage="@"+teamLeaderGithubUser+" please assign this issue to me."
else:
	teamLeaderMessage=''
repositoryUser='bitdubai'

rootIssues=[" - Plug-In__sep__This is the root of your issue structure and must be labeled as SUPER ISSUE. It is closed only when all its children and grand children are closed.",
" - Analisys__sep__This is the Analisys root. It is closed whenever all analisys is done. This issue must be linked to the root of the issue structure.",
" - Implementation__sep__This is the Implementation root. It is closed whenever all implementation is done. This issue must be linked to the root of the issue structure. - Implementation - Developer Class__sep__This issue is closed when this class if fully implemented.",
" - Implementation - Plug-in Root__sep__This issue is closed when this class if fully implemented.",
" - Implementation - Database__sep__This issue is closed when all database classes are fully implemented.",
" - Implementation - Database - Database Factory Class__sep__This issue is closed when this class if fully implemented.",
" - Implementation - Database - Database Constants Class__sep__This issue is closed when this class if fully implemented.",
" - Implementation - Database - Developer Database Factory Class__sep__This issue is closed when this class if fully implemented.",
" - Implementation - Database - Database Factory Exceptions Class__sep__This issue is closed when this class if fully implemented."]


print("------------------ISSUES-------------------")
print("Root Issues--------------------------------\n")
githubConnection = Github(login=githubLogin,password=githubPassword)
issues=githubConnection.issues
for rootIssue in rootIssues:
	rootIssueSep=rootIssue.split("__sep__",2)
	titleGenerated= platform+" - "+layer+" - "+pluginName+" "+rootIssueSep[0]
	bodyGenerated=rootIssueSep[1]+"\n"+teamLeaderMessage
    	print titleGenerated+"\n"+bodyGenerated
    	print "------------------------------------------\n"
	issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#Public interfaces issues
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Public Interfaces"
bodyGenerated="This issue is closed when all public interface's code is written."+"\n"+teamLeaderMessage
print titleGenerated+"\n"+bodyGenerated
print "------------------------------------------\n"
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)
for publicInterface in publicInterfaces:
	titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Public Interfaces - "+publicInterface
	bodyGenerated="This issue is closed when the "+publicInterface+" is written."+"\n"+teamLeaderMessage
    print titleGenerated+"\n"+bodyGenerated
    print "------------------------------------------\n"
	issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#Internal structure issues
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Internal Structure"
bodyGenerated="This issue is closed when all internal structure's code is written."+"\n"+teamLeaderMessage
print titleGenerated+"\n"+bodyGenerated
print "------------------------------------------\n"
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)
for internalStructureClass in internalStructureClasses:
	titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Internal Structure - "+internalStructureClass
	bodyGenerated="This issue is closed when the "+internalStructureClass+" is written."+"\n"+teamLeaderMessage
    print titleGenerated+"\n"+bodyGenerated
    print "------------------------------------------\n"
	issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#Event handling
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Event Handler"
bodyGenerated="This issue is closed when all event handler classes are written."+"\n"+teamLeaderMessage
print titleGenerated+"\n"+bodyGenerated
print "------------------------------------------\n"
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)
for eventHandler in eventHandlers:
	titleGenerated= platform+" - "+layer+" - "+pluginName+" - Implementation - Event Handler - "+eventHandler
	bodyGenerated="This issue is closed when the "+eventHandler+" is written."+"\n"+teamLeaderMessage
    print titleGenerated+"\n"+bodyGenerated
    print "------------------------------------------\n"
	issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#Testing issues
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Testing - Unit Testing"
bodyGenerated="It is closed whenever all testing is done."
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Testing - Integration Testing"
bodyGenerated="It is closed whenever all testing is done."
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#QA issue
titleGenerated= platform+" - "+layer+" - "+pluginName+" - QA"
bodyGenerated="It is closed whenever QA tests are passed."
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)

#Production issue
titleGenerated= platform+" - "+layer+" - "+pluginName+" - Production"
bodyGenerated="It is closed whenever the Plug-in reaches production. It can be re-opened if bug issues are found on production and closed again once they are fixed."
issues.create(dict(title=titleGenerated,body=bodyGenerated,assignee=githubLogin),user=repositoryUser,repo=repository)
