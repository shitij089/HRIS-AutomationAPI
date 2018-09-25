Page Title: HRIS | Login

#Object Definitions
====================================================================================
username                 id           txtUserName
password                 id           txtPassword
signinbtn                classname	  btn 
signinbtn1               name	      Submit
txt_loginPage            css          title 
defaultModules           xpath        //span[contains(text(),'${module Name}')]
chk_rememberMe           id           txtSsi
lnk_logout               xpath        //strong[text()='Logout']
img_logo                 xpath        //a[@href='login.php']/img
scroll_employeeSpeak     css          #scrollerSpeak:nth-child(1)>p:nth-child(1)
scroll_celebration       css          #empscroll:nth-child(1)>div:nth-child(1)
list_celebration         css          #empscroll:nth-child(1)>div
txt_celebrationMsg       css          #empscroll:nth-child(1)
lnk_accessPayroll        xpath        //a[text()='Access Payroll Online']
lnk_reportABug           xpath        //a[text()='Report a Bug']
lnk_HRISRelease          xpath        //a[contains(text(),'HRIS Release')]
txt_copyright            css          .copyright
txt_tabHeading           xpath        //h4[contains(text(),'${tab name}')]
txt_dateAtLoginPanel     css          .event-description>h1
txt_dailyMsg             css          .marqueeCls>h5
checkContent            xpath           .//*[contains(text(),'${tab}')]

btn_feedback             css           .fa.fa-chevron-left.fa-2x
list_hrPolicy            xpath          .//*[@id='leave-policy']/div[2]
txt_hrPolicyName         xpath          //div[text()='${update date}']/preceding-sibling::div
txt_paragraph            xpath          //p/b [contains(text(),'${para name}')]
====================================================================================


@login
--------------------------------
emailaddress:
    width: 350 to 380px
    height: 30 to 50px
