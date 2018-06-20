package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.EmailIds;
import model.User;
import service.EmailIdsServices;
import service.ExcelService;
import service.MailService;

@MultipartConfig													// USE this when coming from jsp with form tag containing encode="multipart/form-data"
public class DMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("Current User");
		String fromMail="", FromEmailIdPassword="", toEmailIdListName="", subject="" ,tempBody = "", excelFilePath="";
		Set <String> attachmentFilePaths = new HashSet<String>();
		Set <String> attachmentFileNames = new HashSet<String>();
		List <String> body = new ArrayList<String>();
		Set<EmailIds> toEmailIdList = null;
		String uploadDirectory = "", uploadPath ="";
		File storeFile, uploadDir;
		try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
	                String fieldName = item.getFieldName().trim();
	                String fieldValue = item.getString().trim();
	                if("txtFromEmailId".equals(fieldName)) {
	                	fromMail = fieldValue;														// 1
	                }
	                else if("txtFromEmailIdPassword".equals(fieldName)) {
	                	FromEmailIdPassword = fieldValue;	                						// 2
	                }
	                else if("txtToEmailIdListName".equals(fieldName)) {
	                	toEmailIdListName = fieldValue;	                							// 3
	                	toEmailIdList = EmailIdsServices.getToEmailIdList(toEmailIdListName, user);
	                }
	                else if("txtEmailSubject".equals(fieldName)) {
	                	subject = fieldValue;														// 4
	                }
	                else if("txtEmailIdBody".equals(fieldName)) {
	                	tempBody = fieldValue;														// 5
	                }
	                System.out.println(fieldName+" "+fieldValue);
	            } else {
	                // Process form file field (input type="file").
	                String fieldName = item.getFieldName();
	                String fileName = item.getName();
	                System.out.println(fieldName+"-"+fileName);
	                if(!("".equals(fileName))) {
	                	if("txtExcelFile".equals(fieldName)) {
		                	uploadDirectory = "uploadedExcelFiles";
		                	uploadPath=request.getSession().getServletContext().getRealPath("") + uploadDirectory;
		                	excelFilePath = uploadPath + File.separator + fileName;												// 6
		                }
		                else if("txtAttachmentFile".equals(fieldName)) {
		                	uploadDirectory = "uploadedAttachmentFiles";
			                uploadPath=request.getSession().getServletContext().getRealPath("") + uploadDirectory;
			                System.out.println("Attached"+uploadPath + File.separator + fileName);
			                attachmentFilePaths.add(uploadPath + File.separator + fileName);										// 7
			                attachmentFileNames.add(fileName);
		                }
		                uploadDir = new File(uploadPath);
	        	        if (!uploadDir.exists()) {
	        	            uploadDir.mkdir();
	        	        }
		                String filePath = uploadPath + File.separator + item.getName();
	                    System.out.println(filePath);
	                    storeFile = new File(filePath);
	                    storeFile.setReadable(true);
	                    storeFile.setWritable(true);
	                    item.write(storeFile);															// saves the file on disk
	                    storeFile = null;
	                }
	            }
	        }
	        
	        
	        /* Changing the body from excel */
	        // 1. Counting the dynamic data and storing start-end index of it with excel variables
	        int numberOfDynamicDatas = 0;
	        List<Integer> startIndexOfDynamicDatas = new ArrayList<Integer>();
	        List<Integer> endIndexOfDynamicDatas = new ArrayList<Integer>();
	        Set<String> dynamicExcels = new HashSet<String>();
	        Set<String> dynamicExcelLetters = new HashSet<String>();
	        List<List<String>> listRow = new ArrayList<List<String>>();
	        int start,end;
	        String excels, excelLetters;
	        for(int i = 0;;i++){
	        	if(i == 0) {
	        		start = tempBody.indexOf("<excel_", 0);
	        		if(start == -1) {
//	        			response.sendRedirect();
	        			return;
	        		}
	        	}
	        	else {
	        		start = tempBody.indexOf("<excel_", endIndexOfDynamicDatas.get(numberOfDynamicDatas-1));
	        		if(start == -1) {
	        			break;
	        		}
	           	}
	        	end = tempBody.indexOf(">",start);
        		if(end == -1) {
//        			response.sendRedirect();
        			return;
        		}
	        	excels = tempBody.substring(start, end+1);
        		System.out.println(excels);
	        	excelLetters = tempBody.substring(start+7, end);

        		startIndexOfDynamicDatas.add(start);
        		endIndexOfDynamicDatas.add(end);
        		dynamicExcels.add(excels);
        		dynamicExcelLetters.add(excelLetters);
        		numberOfDynamicDatas++;
	        }

	        // 2. Fetching that much data from excel
	        listRow = ExcelService.ExcelReader(excelFilePath, listRow);

	        // 3. Changing body
	        List<String> listDynamicExcels = new ArrayList<String>(dynamicExcels);
	        List<String> listDynamicExcelLetters = new ArrayList<String>(dynamicExcelLetters);
	        List<EmailIds> listOfToEmailIdSet = new ArrayList<EmailIds>(toEmailIdList);
	        System.out.println(listOfToEmailIdSet.size());
	        for(EmailIds itemOfToEmailIdList : listOfToEmailIdSet) {
	        	System.out.println("------------FOR "+itemOfToEmailIdList.getEmailId());
	        	int rowIndex = 0;
	        	next_row : for(; rowIndex <= listRow.size()-1 ;rowIndex++) {
	        		System.out.println(itemOfToEmailIdList.getEmailId()+"<-->"+listRow.get(rowIndex).get(0));
	        		if(itemOfToEmailIdList.getEmailId().equals(listRow.get(rowIndex).get(0))) {
	        			int i = 0;
	        			label : for(;i <= listDynamicExcels.size()-1; i++) {
	        				int cellIndex = 0;
	        				for(char column = 'A' ; column <='Z' ; column++) {
	        					System.out.println(listDynamicExcelLetters.size()+" "+listDynamicExcelLetters.get(i)+"<-->"+column+" "+listDynamicExcelLetters.get(i).equals(Character.toString(column)));
	        					if(listDynamicExcelLetters.get(i).equals(Character.toString(column))) {
	        						if("".equals(listRow.get(rowIndex).get(cellIndex).trim())) {
			        					System.out.println(column+(rowIndex+1)+"should not be null");
			        				}
			        				else{
			        					String oldString = listDynamicExcels.get(i);
			        					String newString = listRow.get(rowIndex).get(cellIndex);
			        					System.out.println(oldString+"-------"+newString);
			        					body.add(tempBody.replaceAll(oldString, newString));
				        				break label;
			        				}
	        					}
	        					else
	        						cellIndex++;
	        				}
	        			}
	        			break next_row;
	        		}
		        }
		        if(listRow.size() == rowIndex) {
		        	System.out.println("No Data for: "+itemOfToEmailIdList.getEmailId());
		        }
	        }
	        System.out.println(listRow.size());
	        for(List<String> row : listRow) {
	        	for(String cell : row) {
	        		System.out.print(cell+" ");
	        	}
	        	System.out.println();
	        }
	        int x = 0;
	        for(String item:body) {
	        	System.out.println("\n"+x);
	        	System.out.println(listOfToEmailIdSet.get(x).getEmailId()+"\n"+item);
	        	x++;
	        }
	        int index = 0;
	        for(EmailIds toEmailId : toEmailIdList)
			MailService.SendDynamicMail(toEmailId.getEmailId(), fromMail, FromEmailIdPassword, subject, body.get(index++), attachmentFilePaths, attachmentFileNames);

		} catch (Exception e) {
	        e.printStackTrace();
	    }
		response.sendRedirect("dms.jsp?DynamicMailsSent=true");
	}
	
}
