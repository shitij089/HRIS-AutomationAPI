Page Title: HRIS | PIM

#Object Definitions
====================================================================================
iframe			xpath           //iframe
actionlink		css		img[title='${tab}']
searchbydrpdwn		id		loc_code
searchfortextbox	id		locNameId
searchresultlink	xpath           //a[@class='listViewTdLinkS1']
jobsubtag		css		#jobLink a 
reporttosubtag          css             #report-toLink a
slctSprvsrSbrdnte       xpath           //select[@name='cmbRepType']
slctreportingmthd       xpath           //select[@name='cmbRepMethod']
personalsubtag          xpath           //td[@id='personalLink']/a[contains(text(),'Personal')]
genderradiobtn          xpath           //input[@name='optGender' and @value='${value}']
editbutton		id		btnEdit
jobtitle		id 		cmbJobTitle
empstatus		id		cmbType
joiningdate		id		txtJoinedDate
addlocation		css		#addnewButtons img
selectlocation		id		cmbNewLocationId
fromdate		id		locFrmDate
assignButton		id		assignLocationButton
newEmpIDTxt             xpath           //input[@name="txtEmployeeId1"]
newEmpLastNameTxt       xpath           //input[@name="txtEmpLastName"]
newEmpFirstNameTxt      xpath           //input[@name="txtEmpFirstName"]
noticePeriodTxt         id              custom1
trainingMonthTxt        id              custom4
selectTestGurukul       xpath           //select[@name="custom10"]
selectJobTitle          id              cmbJobTitle
selectEmploymentStatus  id              cmbType
divisionPopBtn          id              divisonPop
subDivision             xpath           //a[@class='title' and contains(.,'${subDivision}')]
migrateLink             id              migrationMain
newEmpId                id              newEmpId
newJoinedDate           id              newJoinedDate
saveMigrtion            id              saveMigrtion
migratedLink            xpath           //a[contains(.,'Migrated')]
employmentDate          id              employ_date
slctemployeebtn         xpath           //input[contains(@onclick,'returnEmpDetail')]
searchdrpdwn		    xpath	     	//select[@name='loc_code']
serachtextbox   	    css 			.dataField
searchButton		    xpath    		//input[@name='btnSearch']
searchrsltlink          xpath	    	//form[@name='standardView']/p[2]/table[4]/tbody/tr[3]/td[2]/a
saveBtn                 xpath           //img[contains(@onclick,'addEXTReportTo')]
logout                  xpath           //strong[contains(.,'Logout')]
btn_tab                 xpath           //a[contains(text(),'${tab name}')]

#dependents
txt_name                css             input[name='txtDepName'] 
txt_FatherDob            id              txtDOB             
drpDwn_relationship     id              txtRelShip

#report to
link_reportTo           xpath           //a[contains(text(),'Report-to')]
drpdwn_superwiser       xpath           //select[@name='cmbRepType']
drpdwn_reportingMethod  xpath           //select[@name='cmbRepMethod']

#child
txt_childName           css             input[name='txtChiName']
txt_childDob            id              aChiDOB

#MaritalStatus
drpdwn_maritalStatus   xpath            //select[@name='cmbMarital']
txt_marriageDate       id               marriageDate

====================================================================================
