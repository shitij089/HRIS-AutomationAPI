Page Title: HRIS | Leave Page

#Object Definitions
====================================================================================
lefttab			xpath		//a[contains(text(),'${tab}')] 
leaveRemaining          xpath                   //td[contains(.,'${leaveType}')]/../td[5]
firstRHInputRadio       xpath                   (//input[@id='compdate'])[1]
sltLeaveType		id			sltLeaveType
txtLeaveFromDate	id			txtLeaveFromDate
txtLeaveToDate		id			txtLeaveToDate
applySucceededMessage   classname                  success
applyFailureMessage     classname                  confirmInnerBox
txtComments		id			txtComments
iframe			id	 		rightMenu
applyButton		xpath		//img[@title='Add']
drpdwn_selectProject	id		erpProject

#Assign Leave
selectemplink		xpath		//form[@id='frmLeaveApp']/table/tbody/tr[2]/td[4]/input[3]
sprvsrselectemployee    id              cmbEmployeeIdPOC
empFullNameOption       xpath           //select[@id='cmbEmployeeIdPOC']/option[contains(.,'${empid}')]
searchbydrpdwn		xpath		//select[@name='loc_code']
serachfortextbox	css			.dataField
searchButton		xpath		//input[@name='btnSearch']	
clearButton		xpath		//input[@name='clear']		
searchresultlink	xpath		//form[@name='standardView']/p[2]/table[4]/tbody/tr[3]/td[2]/a
txt_hoursForBooto       css             .hrsTxtDes
txt_cmntForBooto        id              txtComments
drpdwn_duration      xpath      //select[@id='txtLeaveTotalTime']
txt_duration     xpath          //option[text()='${Day}']

duration		id			txtLeaveTotalTime
assignLeaveLink         xpath           //a[@target='rightMenu' and contains(.,'Assign Leave')]
		
#LeaveListTab
allLeaveCheckBox	id			allCheck
takenLeaveCheckBox	xpath		//form[@id='frmFilterLeave']/table/tbody/tr[2]/td[3]/input[6]
txtFromDate		id			txtFromDate
txtToDate		id			txtToDate
searchLink		xpath		//input[@title='Search']
dateList		xpath		//form[@id='frmCancelLeave']/table/tbody/tr
dateOfLeave		xpath		//form[@id='frmCancelLeave']/table/tbody/tr[${row}]/td[2]/a
actionOnLeave		xpath		//form[@id='frmCancelLeave']/table/tbody/tr[${row}]/td[6]/select
saveButton		xpath		//form[@id='frmCancelLeave']/table/tbody/tr[${row}]/td[10]/img[1]

drpDwn_cancelLeave      xpath    //td[text()='${empName}']/following-sibling::td[2][text()='${leaveType}']/../td[6]/select
btn_save         xpath      //td[text()='${empName}']/../td[10]/img[1]
list_hourForDay                 xpath           .//*[contains(@id,'tdText${2016-07-25}')]/..//tbody/tr[2]/td

txt_InvalidDatePtoMsg            xpath              .//*[@id='duplicateWarning']/div

drpdwn_employeeName              id                cmbEmployeeIdPOC
scr_assignEmp                    xpath          //option[contains(text(),'${empId}')]
list_text_assignEmpOptions		xpath			//*[@id='cmbEmployeeIdPOC']/option

drpdwn_monthYr     id            monthYr
searchName         xpath         .//*[contains(text(),'${name}')]/..//*[contains(text(),'${date}')]
drpdwnstatus       name          cmbStatus[]
drpdwnstatus1      xpath          .//*[@name='cmbStatus[]']/option[text()='Cancelled']
button_save        xpath          .//*[@class='saveCur']
drpDwnFromTimeToToTime       xpath          .//*[@id='${tab}']
leaveStatusText             xpath      //*[contains(text(),'${tab}')] 
txt_confirmBox              xpath       //div[@class='confirmBox']/div
chk_confirmDate             xpath        //td[contains(text(),'${tab}')]/..//input   

radio_date                  xpath        //table[@id='compofftable']//*[contains(text(),'${date}')]/../td/input


    
====================================================================================

