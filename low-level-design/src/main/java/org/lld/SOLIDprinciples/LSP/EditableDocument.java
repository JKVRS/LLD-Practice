package SOLIDprinciples.LSP;

interface Document {
    public abstract void open();
    public abstract String getData();
}

interface Editable{
    public abstract void save(String newData);
}

// Base class
public class EditableDocument implements Document, Editable {
    protected String data;
    public EditableDocument(String data) {
        this.data = data;
    }
    // open
    @Override
    public void open(){
        System.out.println("Document Open. Data: "+ data.substring(0, Math.min(data.length(), 202))+" ...");
    }
    // save
    @Override
    public void save(String newData){
        this.data = newData;
        System.out.println("Document Saved");
    }
    // getData
    @Override
    public String getData(){
        return data;
    }
}

// Lets we need Readonly Document
//class ReadonlyDocument extends EditableDocument {
//    public ReadonlyDocument(String data){
//        super(data);
//    }
//    @Override
//    public void save(String newData){
//        // readonly document can not allow editing
//        throw new UnsupportedOperationException("Can not save a read-only");
//    }
//}

class ReadonlyDocument implements Document {
    private final String data;

    public ReadonlyDocument(String data){
        this.data = data;
    }

    @Override
    public void open(){
        System.out.println("Read-Only Document opened. Data: " + preview());
    }

    @Override
    public String getData(){
        return data;
    }

    public String preview(){
        return data.substring(0, Math.min(data.length(), 20)) + "...";
    }
}

class DocumentProcessor {
    public void process(Document doc){
        doc.open();
        System.out.println("Document Processed.");
    }

    /**
     * As per LSP S (super class Type) object can be replaceable by its T (Sub class Type) object
     * Note: But this programme should behave correctly
     * Here ReadonlyDocument type is not abeying the baseclass expectation which is all the document is savable -(throwing exception to the client as a surprise)
     * This is Violation OF LSP
     * To fix it we can utilize the abstraction using (abstract class or interface) - to sepertat the responsibility
     * @param doc
     * @param additionalInfo
     */

    public void processAndSave(Document doc, String additionalInfo){
        if(! (doc instanceof Editable)){
            throw new IllegalArgumentException("Document is not editable.");
        }
        doc.open();
        String currentData = doc.getData();
        String newData = currentData + "| Processed: " + additionalInfo;
        ((EditableDocument) doc).save(newData); // Critical assumption: All document are savable
        System.out.println("Document Processing complete");
    }



    public static void main(String[] args){
        // RegularDoc
        Document regularDocument = new EditableDocument("Initial Project Proposal content.");
        // Read-only Document
        Document readOnlyDocument = new ReadonlyDocument("Top secret Government Data");

        DocumentProcessor processor = new DocumentProcessor();

        System.out.println("--- Processing Regular Document---");
        processor.processAndSave(regularDocument, "Reviewed by ALice");

        System.out.println("\n---Processing Readonly Document---");
        processor.processAndSave(readOnlyDocument, "Reviewed by Bob");
    }
}

