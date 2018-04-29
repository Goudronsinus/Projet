package objects;
import javax.media.opengl.GL2;

import worlds.World;

public class Cloud extends UniqueObject{
	
	public Cloud ( int __x , int __y , World __world )
	{
		super(__x,__y,__world);
	}
	
    public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

    	/*int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

        gl.glColor3f(0.5f,0.5f,0.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight*0.5f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight*0.5f);
 
        gl.glColor3f(0.6f,0.5f,0.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight*0.5f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight*0.5f);
        
        gl.glColor3f(0.55f,0.5f,0.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight*0.5f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, 1.f*normalizeHeight*0.5f);

        gl.glColor3f(0.45f,0.5f,0.f);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY+lenY, 0.9f*normalizeHeight*0.95f);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY+lenY, 0.9f*normalizeHeight);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY-lenY, 0.9f*normalizeHeight);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY-lenY, 0.9f*normalizeHeight*0.95f);

        gl.glColor3f(0.5f,0.5f,0.f);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY-lenY, 0.1f*normalizeHeight);
        gl.glVertex3f( offset+3*x2*stepX-lenX, offset+y2*stepY+lenY, 0.1f*normalizeHeight);
        gl.glVertex3f( offset+3*x2*stepX+lenX, offset+y2*stepY+lenY, 0.1f*normalizeHeight);
        gl.glVertex3f( offset+3*x2*stepX+lenX, offset+y2*stepY-lenY, 0.1f*normalizeHeight);
    	*/
    	
    	/* gl.glTranslatef(0f, 0f, -2.5f);
         gl.glBegin(GL2.GL_LINES);
         gl.glVertex3f(-0.75f,0f,0);
         gl.glVertex3f(0f,-0.75f, 0);
         gl.glEnd();
		*/
         //3d line
         gl.glBegin(GL2.GL_LINES);

         //3 units in to the window
         gl.glVertex3f(-0.75f,0f,3f);
         gl.glVertex3f(0f,-0.75f,3f);
         gl.glEnd();

         //top
         gl.glBegin(GL2.GL_LINES);
         gl.glVertex3f(-0.75f,0f,0);
         gl.glVertex3f(-0.75f,0f,3f);
         gl.glEnd();

         //bottom
         gl.glBegin(GL2.GL_LINES);
         gl.glVertex3f(0f,- 0.75f, 0);
         gl.glVertex3f(0f,-0.75f,3f);
         gl.glEnd();    
    }


}
