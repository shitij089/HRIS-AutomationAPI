Page Title: HRIS | Home Page

#Object Definitions
====================================================================================
welcomemsg              classname      	welcome
headertag		        xpath	 	//a[text()='${tab}'] 
tlsum			        xpath	     	//ul[@id='menu']/li[2]/a
emptlsum		        xpath	 	//div[@id='menu24Content']/table/tbody/tr[2]/td/a
resourcemngmnttag       xpath           //a[contains(.,'Resource')]
link_sideBarLinkAtHome  xpath           .//*[contains(text(),'${tab}')]
iframe1                 id              rightMenu
input_afterAddButton    xpath             .//*[@id='${tab}']
checkBox_checkCouter    name              counterState
button_save             id                  save
link_logout             xpath             //strong[text()='Logout']
button_Tab              xpath             //table[@class='tabContainer']//a[contains(text(),'${Admin}')]
text_label              xpath             //*[contains(text(),'${tab}')]
button_next             css               .fc-button-next
button_pre              css               .fc-button-prev
size_birthday           css               #birhday p
label_birthday          css               #birhday p:nth-child(${tab})
text_RemindersContent   css               #infoDiv>div:nth-child(${tab})
txt_PersonalInfoContent css               #persInfo>tbody>tr>td:nth-child(${tab})
img_Personal            css               #persContent>table>tbody>tr>td>img
logo_qainfotech         css               .logo.onepage.logo_lg
events_label            css               .fc-event-hori:nth-child(${tab})

txt_hourForGivenDay             xpath           .//*[contains(@id,'tdText${date}_')]//tbody/tr[2]/td 
txt_personalInfo        xpath             //table[@id='persInfo']//b[contains(text(),'${InfoName}')]/..//following-sibling::td
img_logo                css               a[href='index.php'] img
txt_welcome             css               .welcome
txt_footer              xpath             //td[@id='rightMenuHolder']/table[2]//tr[${1}]
txt_headingHRDoc        css               html>body>h2
txt_documentHeading     xpath             .//*[@id='frmSummary']/table/tbody[1]
list_text               xpath             (//*[@class='list-inline designation-list'])[${tab}]/li
reporting		xpath		//a[contains(text(),'${Reporting}')]
====================================================================================


