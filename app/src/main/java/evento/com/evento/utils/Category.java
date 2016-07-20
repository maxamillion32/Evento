package evento.com.evento.utils;

/**
 * Created by Aya on 6/17/2016.
 */
public class Category
{
        String name = null;
        boolean selected = false;

public Category(String name, boolean selected) {
        super();
        this.name = name;
        this.selected = selected;
        }


public String getName() {
        return name;
        }
public void setName(String name) {
        this.name = name;
        }

public boolean isSelected() {
        return selected;
        }
public void setSelected(boolean selected) {
        this.selected = selected;
        }

        }
