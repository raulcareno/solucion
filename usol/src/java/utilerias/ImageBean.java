/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

/**
 *
 * @author inform
 */
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 

 
@ManagedBean
@RequestScoped
public class ImageBean
{
 
 public static final int MAX_IMAGE_COUNT = 15;
// private static final Logger logger = LoggerFactory.getLogger(ImageBean.class);
 private List<photo> images;
 
 public ImageBean()
 {
  images = new ArrayList<photo>();
   
  for (int ctr = 0; ctr < MAX_IMAGE_COUNT; ctr++)
  {
   photo photo = new photo();
   photo.setId(ctr);
   photo.setTitle("Mock Title #" + ctr);
   String description = "This photo is used to represent item #" + ctr + " in a selection of images."; 
   photo.setDescription(description);
   images.add(photo);
//   logger.info("Added Photo {} to collection.", ctr);
  }
 }
 
 public List<photo> getImages()
 {
  return images;
 }
 
 public void setImages(List<photo> images)
 {
  this.images = images;
 }
 
}