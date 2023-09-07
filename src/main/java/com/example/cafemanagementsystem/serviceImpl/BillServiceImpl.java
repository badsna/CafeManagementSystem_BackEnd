package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.jwt.JwtAuthenticationFilter;
import com.example.cafemanagementsystem.model.Bill;
import com.example.cafemanagementsystem.model.Product;
import com.example.cafemanagementsystem.repo.BillRepo;
import com.example.cafemanagementsystem.service.BillService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepo billRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> billRequestDto) {
        try {
            log.info("Inside generateReport");
            //if billRequestDto contains valid data or not
            String fileName;
            if (validateRequestMap(billRequestDto)) {
                log.info("Validation Successful");
                //
                if (billRequestDto.containsKey("isGenerate") && !(Boolean) billRequestDto.get("isGenerate")) {
                    fileName = (String) billRequestDto.get("uuid");
                } else {
                    //else part generate unique file name , pass it into billRequestDto to insert into database
                    fileName = CafeUtils.getUUID();
                    billRequestDto.put("uuid", fileName);
                    insertBill(billRequestDto);
                }

                //to generate the pdf
                String data = "Name: " + billRequestDto.get("name") + "\n" + "Contact Number: " + billRequestDto.get("contactNumber") + "\n " + "Email: " + billRequestDto.get("email") +
                        "\n" + "Payment Method: " + billRequestDto.get("paymentMethod");

                //location to store document
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

                //open the document
                document.open();
                setRectangleInPdf(document);

                //header of pdf
                Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                //data we have in paragraph
                Paragraph paragraph = new Paragraph(data+"\n\n", getFont("Data"));
                document.add(paragraph);

                //table of pdf
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                //cause our data is in json
                //set the rows into the table
                JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) billRequestDto.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRow(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                //footer of pdf
                Paragraph footer = new Paragraph("Total : " + billRequestDto.get("totalAmount") + "\n"
                        + "Thank you for visiting.Please visit again!!!!", getFont("Data"));
                document.add(footer);

                //close document
                document.close();

                System.out.println(fileName);

                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);

            }
            return CafeUtils.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRow(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRow");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("inside addTableHeader");
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String header) {
        log.info("Inside getFont");
        switch (header) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("inside setRectangleInPdf");
        Rectangle rectangle = new Rectangle(557, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);

    }

    private boolean validateRequestMap(Map<String, Object> billRequestDto) {
        log.info("Inside validateRequestMap");
        log.info(billRequestDto.toString());
        return billRequestDto.containsKey("name") &&
                billRequestDto.containsKey("contactNumber") &&
                billRequestDto.containsKey("email") &&
                billRequestDto.containsKey("paymentMethod") &&
                billRequestDto.containsKey("productDetails") &&
                billRequestDto.containsKey("totalAmount");
    }

    private void insertBill(Map<String, Object> billRequestDto) {
        log.info("Inside insertBill");
        try {
            Bill bill = new Bill();
            bill.setUuid((String) billRequestDto.get("uuid"));
            bill.setName((String) billRequestDto.get("name"));
            bill.setEmail((String) billRequestDto.get("email"));
            bill.setContactNumber((String) billRequestDto.get("contactNumber"));
            bill.setPaymentMethod((String) billRequestDto.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) billRequestDto.get("totalAmount")));

            String productDetailsJson = (String) billRequestDto.get("productDetails");

            bill.setProductDetails((String) billRequestDto.get("productDetails"));
            bill.setCreatedBy(jwtAuthenticationFilter.getCurrentUser());
            billRepo.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list=new ArrayList<>();
        if(jwtAuthenticationFilter.isAdmin()){
          list=billRepo.findAll();
        }else{
            list=billRepo.findByCreatedBy(jwtAuthenticationFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> pdfRequestDto) {
        log.info("Inside getPdf:pdfRequestDto{}",pdfRequestDto);
        try {
            byte[] byteArray=new byte[0];
            if(!pdfRequestDto.containsKey("uuid") && validateRequestMap(pdfRequestDto))
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
            String filePath=CafeConstants.STORE_LOCATION+"\\"+(String) pdfRequestDto.get("uuid")+".pdf";

            if(CafeUtils.isFileExists(filePath)){
                byteArray=getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }else {
                pdfRequestDto.put("isGenerate",false);
                generateReport(pdfRequestDto);
                byteArray=getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws IOException {
        File initialFile=new File(filePath);
        InputStream targetStream=new FileInputStream(initialFile);
        byte[] byteArray= IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            if (jwtAuthenticationFilter.isAdmin()) {
                Optional<Bill> bill = billRepo.findById(id);
                if (bill.isPresent()) {
                    billRepo.deleteById(id);
                    return CafeUtils.getResponseEntity("Bill Deleted Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Bill id doesnt exists.", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);    }


}
