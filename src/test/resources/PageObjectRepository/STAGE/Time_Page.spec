Page Title: HRIS | PIM

#Object Definitions
====================================================================================
iframe				xpath 		//iframe
lefttab				xpath		//a[text()='${tab}'] 
empidtextfield			id			empIdSearch
ptosumlink			id			ptoImage
sickleavebalance		xpath		//table[@id='ptoTbl']//th[contains(text(),'SL Available(Earned + Advance)')]/following-sibling::td
ptoleavebalance			xpath		//table[@id='ptoTbl']//th[contains(text(),'PTO Earned')]/following-sibling::td

#Onsite Punches

singleradiobutton		id			Single
selectemplink			id			popEmp
searchbydrpdwn			xpath		//select[@name='loc_code']
serachfortextbox		css			.dataField
searchButton			xpath		//input[@name='btnSearch']	
clearButton			xpath		//input[@name='clear']		
searchresultlink		xpath		//form[@name='standardView']/p[2]/table[4]/tbody/tr[3]/td[2]/a
startDate			id			txtStartDate
endDate				id			txtEndDate
searchButtonTwo			css			#ShowIndividual input.button1
editButton			id			btnEdit
dateList			xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr
dateCheckBox			xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[2]/input
dateDay				xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[3]
inTime				xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[4]/input
outTime				xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[5]/input
elapsedTime			xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[6]/input
total				xpath		//form[@id='frmPunchTime']/table[2]/tbody/tr[${tablerownumber}]/td[7]

#TimeSheet
selectmonth			id			monthUSA
statusOfDay			xpath		//td[@id='tdText${date}_0113']/table/tbody/tr[2]/td/font
statusOfDayOff			xpath		//td[@id='tdText${date}_0113']/table/tbody/tr[2]/td
selectMonth                     id              monthUSA
applyAHlink                     xpath           //a[contains(@href,'apply_addtional_time')]
closeBtn                        xpath           //button[contains(text(),'Close')]
applyAHBtn                      xpath           //button[contains(text(),'Apply')]
AHDate                          id              applyDate
selectPreviousNDate             xpath           //td[contains(@class,'ui-datepicker-today')]/preceding-sibling::td[${day}]
selectFutureNDate               xpath           //td[contains(@class,'ui-datepicker-today')]/following-sibling::td[${day}]
selectTodaysDate                xpath           //td[contains(@class,'ui-datepicker-today')]
AHTime                          css             .apply-cls
applyAHTimeBtn                  id              btnSubmit
applyAHFailureMessage           id              errorMsg
txt_weekHours                   xpath           .//*[contains(@id,'tdText${startDate}')]//..//td[9]//b
list_valueForDay                xpath           .//*[contains(@id,'tdText${date}')]/../td/input
list_hourForDay                 xpath           .//*[contains(@id,'tdText${date}')]/..//tbody/tr[2]/td
txt_valueForDay                 xpath            .//*[contains(@id,'tdText${date}')]/input
txt_hourForGivenDay             xpath           .//*[contains(@id,'tdText${date}_')]//tbody/tr[2]/td 
frame                           id               rightMenu
drpdwn_month                    css             #monthUSA
txt_monthName                   xpath           //option[text()='${month name}']

#Assign holiday
btn_add                          css            #btnAdd
txt_date                         id             txtOffDate
txt_holiday                      id             holidayName
scr_division                     xpath          //option[text()='${Division Name}']
scr_assignemp                    xpath          //option[contains(text(),'${empId}')]
btn_moveRight                    id             proMoveToRight
btn_save                         xpath          //img[contains(@src,'btn_save')]

idPreviousWeek                   xpath          .//*[@id='tdText${2016-09-27}_${1021}']/../preceding-sibling::tr[4]//td[2]
timeOfGivenDate                  xpath          .//*[@id='tdText${2016-09-25}_${1021}']//tr[2]/td
txt_leaveOfGivenCategory         xpath          //th[text()='${leave type}']/following-sibling::*
img_leaveInfo                    id             ptoImage
txt_dayInformation               xpath           .//*[@id='tdText${date}_${emp id}']/input
txt_timeValueForGivenDate        xpath          .//*[@id='tdText${2016-09-16}_${130502}']//tr[2]/td
list_allSandwitchInGivenPage     xpath          //td[contains(@class,'clsAll') and contains(.,"SW")]

txt_startDateOfGivenWeek         xpath           //b[text()='Week ${3}']/../following-sibling::td[1]
inputBoxAfterQaitPunches1         xpath           (.//*[@class='row'])[2]/div[${tab}]/input
inputBoxAfterQaitPunches2         xpath           (.//*[@class='row'])[2]/div[${tab}]/button
outTime_InputBox                  xpath           .//*[@class='form-control txtData']
user_info                         xpath            .//*[@id='users_info']

yearSelection                     xpath             .//*[@id='yearUSA']
txt_OutTimeConf               xpath      //font[text()='Saved']
====================================================================================
