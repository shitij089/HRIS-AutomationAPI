#Object Definitions
====================================================================================
#LoginPage
txt_userName                  id             input_username
txt_password                  id             input_password
btn_go                        id             input_go

frame_navigation              id             frame_navigation
frame_table                   id             frame_content
txt_searchTable               id             fast_filter
link_goTable                  id             qainfotech_hris.hs_hr_emp_raw_punches
btn_tableTab                  xpath          //a[contains(text(),'${tabName}')]
txt_tableData                 xpath          (//td[contains(text(),'${fieldName}')]//following-sibling::td[4]//*)[${num}]
btn_submitTableData           xpath          (//input[@value='Go'])[1]
txt_searchResult              css             .success

##edit details
txt_tableSearchData           xpath          //th[text()='${emp_other_id}']/..//td[4]/input[1]  
btn_edit                      xpath           (//span[@class='nowrap'])[1]
updated_time                  xpath              .//*[@id='field_5_3']
====================================================================================