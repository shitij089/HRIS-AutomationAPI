Page Title: HRIS | Resource Management Page

#Object Definitions
====================================================================================
closeModalDialog                xpath                   //button[contains(.,'Close')]
myProjectstab                   id                      billing
searchProject                   id                      mainSearch
searchEngineers                 id                      searchTxt
projectLink                     css                     .proCursorCls 
searchResource                  id                      empNameSearchEmp
genericSearchById               id                      ${tab}
empSearchResult                 xpath                   //a[contains(.,'${empID}')]
submitBtn                       id                      submitBtnEmp
updateBilling                   css                     .dollar-cls
toggleCheckBox                  css                     .chkAll
allEmployeesCheckBox            xpath                   //input[contains(@class,'chkChild')]
latestEmployeeCheckBox          xpath                   (//input[contains(@class,'chkChild')])[${index}]
selectDate                      xpath                   //p[contains(@id,'${date}')]
additionalHourTxt               id                      hourVal_3
comment                         id                      comment
saveAHBtn                       css                     .saveBillCls 
button_updateBilling            xpath                   .//*[@id='users']/tbody/tr[${tab}]/td[${tab}]
button_closeResourceFromProject xpath                    .//*[contains(text(),'${tab}')]//..//td[7]
txt_resultInfo                  xpath                    .//font[contains(text(),'Successfully Added')]
select_dropDown                 xpath                    .//*[contains(text(),'${tab}')]
txt_footer                      css                     #push>span>b
img_hrisLogo                    xpath                   //a[@class='navbar-brand']/img
txt_defaultDate                 xpath                     //a[contains(@class,'dp-item dp-selected')]
reporting_heading				xpath					//div[@class='row']/h4[contains(text(),'Reporting')]
manage_rank_btn					xpath					//a[contains(@class,'btn-md')]
search_text						xpath					//div[@id='users_filter']/label
search_text_field				xpath					//div[@id='users_filter']/label/input
report_to_list					xpath					.//*[contains(@id,'supNameId')]
emp_name_list					xpath					//td[contains(@class,'cursorPnt')]
manage_rank_btn					xpath					//a[text()='Manage Rank']
rank_weightage					xpath					//input[contains(@id,'rankVal')]
save_btn						xpath					.//*[contains(@id,'save')]
new_rank_weightage				xpath					//div[contains(@id,'AddPanel')]/div[contains(@class,'form-group')]/input[@id='rankId']
rank_name_field					xpath					//div[contains(@id,'AddPanel')]/div[contains(@class,'form-group')]//input[@id='rankName']
save_btn_for_zero				xpath					.//*[@id='save']
ranks_list						xpath					.//*[contains(@id,'rankVal')]
ranks_name_list					xpath					.//*[contains(@id,'rankNameId')]
delete_rank						xpath					.//*[contains(@id,'del')]
search_results					xpath					//table[@id='users']//tr[contains(@class,'panel')]/td[1]
input_editName                  xpath                   //table[@id='users']//tr[contains(@class,'panel')]/td[3]/input
search_on_top					xpath					//input[@id='engTimeSearch']
desired_search_result			xpath					//li[contains(@class,'menu-item')]
emp_name_bill_cal				xpath					.//*[@id='searchContainerTxt']
billing_calender				xpath					//div[@class='mainContainer']/table
close_billing_cal				xpath					//div[contains(@class,'billing-cl')]//button[@class='close']
settings_and_tags				xpath					//a[@id='addstatustag']/i															
settings_submenu				xpath					//ul[@class='mm-listview']/li
settings_submenu_items			xpath					//a[contains(@href,'${options}')]
submenu_item_page_heading		xpath					//div[@class='page-header']//h3
questionnaire_report_option		xpath					//a[@href='/empFeedback/report']
settings_submodules				xpath					//a[contains(@href,'section')]
slideout						xpath					//a[@id='hamburger']
reporting_tab					xpath					.//*[@id='addpoc']
expand_settings					xpath					//a[@class='mm-next']		
slideout_prev					xpath					//a[contains(@class,'mm-prev mm-btn')]
link_calender                   xpath                   //a[contains(@class,'dp-nav-${left}')]
#For checking all shadows and Non-belleble employees present
link_employeesCategory          xpath                   //*[contains(text(),'${employeesCategory}')]
btn_close                       xpath                   //button[text()='Close']
txt_categoryName                xpath                   //h4[contains(text(),'${category name}')]
txt_employeeInfoName            xpath                   //table[@id='allEmpTbl']//tr[2]/td[${num}]
btn_category_permission			xpath					//a[@href='#sectionC']
input_division_software			xpath					//input[@id='txtName7']
dropdwon_ESS_option				xpath					//li[contains(@class,'menu-item')]
list_user_given_permission		xpath					//span[contains(@id,'empSpan7')]
btn_profile						xpath					//a[contains(@class,'profile-btn')]/span
profile_back_btn				xpath					//span[contains(text(),'Back')]
logout_btn						xpath					//a/strong[text()='Logout']
btn_dashboard					xpath					.//*[@id='dashboard']/span
division_name					xpath					(//table[contains(@class,'table-border-cls')]//tr/td[1])[1]
division_name_list				xpath					//div[@class='row']/h4
x_last_user						xpath					(//div[@id='user7']//span[contains(@id,'empSpan')])[last()]/font
link_support					xpath					//div[contains(@class,'bg-green')]//h6[contains(text(),'Support')]
support_page_title				xpath					//div[@id='myModal']//h4[@class='modal-title']
support_table_columns			xpath					//div[contains(@id,'emptable_parent')]//table[contains(@id,'emptable')]//th
first_emp_name_support			xpath					(//td[contains(@class,'click-bread')])[1]
first_emp_name_billing_cal		xpath					(//div[@id='tblInfo']//span[contains(@class,'bill-cal-key')]/following-sibling::span)[1]
billing_month					xpath					//div[contains(@class,'slick-active')]
first_actual_billable_emp		xpath					(//td[@class='click-support click-enabled'])[1]
myprojects_tab					xpath					//a[@id='billing']
myproject_first_dollar			xpath					(//img[@class='dollar-cls'])[1]
toggle_Uncheck_all				xpath					//input[@class='chkAll']
checkbox_third_emp				xpath					(.//*[contains(@id,'chk')])[3]
billing_next_month_btn			xpath					//button[@class='slick-next']
select_date_billing_cal			xpath					//p[contains(@id,'${date}')]
billing_comment					xpath					.//*[@id='comment']
shadow_hours					xpath					//div[@id='tagIds2']//input
shadow_hours_new				xpath					//div[@class='tagBind']//input[@id='hourVal_2']
shadow_save_btn					xpath					//button[contains(@class,'saveBillCls')]
alert_heading					xpath					.//*[@id='shadowModal']//h4[@class='modal-title']
alert_body						xpath					.//*[@id='shadowModal']//div[@class='modal-body']
close_alert_btn					xpath					//div[@id='shadowModal']//button[contains(text(),'Close')]
close_error_btn					xpath					//div[@id='shadowModal']//*[@id='cancelEditFlg']
delete_shadows					xpath					//button[contains(@class,'delBillCls')]
links_inside_engg_box_list		xpath					//div[contains(@class,'bg-green')]//div[contains(@id,'tag_')]//h6
list_header_engg_box			xpath					//div[@class='strip-cls']/a
heading_engg_box				xpath					//div[contains(@class,'bg-green')]//h4[contains(@id,'all_emp')]
engg_box_body					xpath					//div[@id='myModal']//div[@class='modal-body']
links_heading_after_clicking	xpath					//div[@id='myModal']//h4[@class='modal-title']
btn_close_link					xpath					//div[@id='myModal']//button[@class='close']
all_division_heading			xpath					//div[@class='row']//h4[contains(@id,'all_emp')]
monthly_btn						xpath					//button[@id='month']
monthly_submodules				xpath					//ul[contains(@class,'nav-tabs')]/li/a
graph_icon						xpath					//div[@class='floatGraphIcon']
monthly_first_project			xpath					(.//*[@id='projectMonthDiv']//div[contains(@class,'col-sm-3')]//h4)[1]
project_title_monthly			xpath					//div[@id='projectModal']//h4[@class='modal-title']
close_billing_cal_monthly		xpath					//div[@id='projectModal']//button[@class='close']
monthly_projects_submenu		xpath					//a[@id='persFact']
search_monthly_projects_submenu xpath					//div[@id='projects_filter']//input
search_results_projects			xpath					//table[@id='projects']//tbody[@role='alert']//tr//td[2]
search_results_manager			xpath					//table[@id='projects']//tbody[@role='alert']//tr//td[3]
search_results_id				xpath					//table[@id='projects']//tbody[@role='alert']//tr//td[1]
lnk_firstProject                xpath                   //*[@id='projectMonthDiv']/div[2]//div[@class='row']/h4
txt_searchEmployee              css                     #projTable_filter>label>input
txt_getIdOfEmployee             xpath                   //td[text()='${empName}']/../td[1]
txt_projectDetails              xpath                   //table[@id='projects']//tbody/tr[1]/td[${1}]
list_projectTableInfo           xpath                   //table[@id='projTable']//tbody[@role='alert']//tr//td[7]
lnk_invoiceDataInfo             xpath                   //table[@id='deltaTbl']//tbody//td[${row Num}]
lnk_dashboardMenu               xpath                   //a[text()='${menu}']
list_RMSAddlHours               xpath                   //table[@id='projTable']//tbody[@role='alert']//tr//td[${7}]
lnk_billingSummaryInOutstandingInvoice    xpath         (//table[@id='users']//td[contains(@onclick,'viewBillingSummary')])[${1}]
lnk_outstandingInvoiceDataSummary         xpath         (//table[@id='users']//tbody//td[@class='click-enabled'])[${2}]
txt_joinDepartment                        xpath         //h4[text()='${department}']/following-sibling::div
===========================================================================================================================
