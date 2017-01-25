
/* First created by JCasGen Thu May 12 15:02:38 CEST 2016 */
package fr.lirmm.advanse.chv.acquisition.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Model a single post on a forum
 * Updated by JCasGen Fri May 20 16:47:12 CEST 2016
 * @generated */
public class Post_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Post_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Post_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Post(addr, Post_Type.this);
  			   Post_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Post(addr, Post_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Post.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.lirmm.advanse.chv.acquisition.type.Post");
 
  /** @generated */
  final Feature casFeat_SubForum;
  /** @generated */
  final int     casFeatCode_SubForum;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSubForum(int addr) {
        if (featOkTst && casFeat_SubForum == null)
      jcas.throwFeatMissing("SubForum", "fr.lirmm.advanse.chv.acquisition.type.Post");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SubForum);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSubForum(int addr, String v) {
        if (featOkTst && casFeat_SubForum == null)
      jcas.throwFeatMissing("SubForum", "fr.lirmm.advanse.chv.acquisition.type.Post");
    ll_cas.ll_setStringValue(addr, casFeatCode_SubForum, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Author;
  /** @generated */
  final int     casFeatCode_Author;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAuthor(int addr) {
        if (featOkTst && casFeat_Author == null)
      jcas.throwFeatMissing("Author", "fr.lirmm.advanse.chv.acquisition.type.Post");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Author);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAuthor(int addr, String v) {
        if (featOkTst && casFeat_Author == null)
      jcas.throwFeatMissing("Author", "fr.lirmm.advanse.chv.acquisition.type.Post");
    ll_cas.ll_setStringValue(addr, casFeatCode_Author, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Id;
  /** @generated */
  final int     casFeatCode_Id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public long getId(int addr) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "fr.lirmm.advanse.chv.acquisition.type.Post");
    return ll_cas.ll_getLongValue(addr, casFeatCode_Id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, long v) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "fr.lirmm.advanse.chv.acquisition.type.Post");
    ll_cas.ll_setLongValue(addr, casFeatCode_Id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Thread;
  /** @generated */
  final int     casFeatCode_Thread;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getThread(int addr) {
        if (featOkTst && casFeat_Thread == null)
      jcas.throwFeatMissing("Thread", "fr.lirmm.advanse.chv.acquisition.type.Post");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Thread);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setThread(int addr, String v) {
        if (featOkTst && casFeat_Thread == null)
      jcas.throwFeatMissing("Thread", "fr.lirmm.advanse.chv.acquisition.type.Post");
    ll_cas.ll_setStringValue(addr, casFeatCode_Thread, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Title;
  /** @generated */
  final int     casFeatCode_Title;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTitle(int addr) {
        if (featOkTst && casFeat_Title == null)
      jcas.throwFeatMissing("Title", "fr.lirmm.advanse.chv.acquisition.type.Post");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Title);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTitle(int addr, String v) {
        if (featOkTst && casFeat_Title == null)
      jcas.throwFeatMissing("Title", "fr.lirmm.advanse.chv.acquisition.type.Post");
    ll_cas.ll_setStringValue(addr, casFeatCode_Title, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Post_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_SubForum = jcas.getRequiredFeatureDE(casType, "SubForum", "uima.cas.String", featOkTst);
    casFeatCode_SubForum  = (null == casFeat_SubForum) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SubForum).getCode();

 
    casFeat_Author = jcas.getRequiredFeatureDE(casType, "Author", "uima.cas.String", featOkTst);
    casFeatCode_Author  = (null == casFeat_Author) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Author).getCode();

 
    casFeat_Id = jcas.getRequiredFeatureDE(casType, "Id", "uima.cas.Long", featOkTst);
    casFeatCode_Id  = (null == casFeat_Id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Id).getCode();

 
    casFeat_Thread = jcas.getRequiredFeatureDE(casType, "Thread", "uima.cas.String", featOkTst);
    casFeatCode_Thread  = (null == casFeat_Thread) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Thread).getCode();

 
    casFeat_Title = jcas.getRequiredFeatureDE(casType, "Title", "uima.cas.String", featOkTst);
    casFeatCode_Title  = (null == casFeat_Title) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Title).getCode();

  }
}



    