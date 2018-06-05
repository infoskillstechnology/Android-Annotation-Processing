# Android-Annotation-Processing
Custom Android Annotation Processing
 ** Define an annotation BindView for mapping.
 `@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
} `

Place `BindView` annotation on a View class variable with the view's id.

`public class MainActivity extends AppCompatActivity {
    ...
    @BindView(R.id.txtView)
    TextView txtView;
    ...
}
`

Create a class that does the assignment of the TextView object defined in the XML with the id tv_name to the variable tvName.

`public class ViewBinder {
    /*
     * annotations for activity class
     * @param target is the activity with annotations
     */
    public static void bind(final Activity target){
        bindViews(target, target.getClass().getDeclaredFields(),
    }

    /*
     * initiate the view for the annotated public fields
     * @param obj is any class instance with annotations
     * @param fields list of methods in the class with annotation
     * @param rootView is the inflated view from the XML
     */
    private static void bindViews(final Object obj, Field[] fields, View rootView){
        for(final Field field : fields) {
            Annotation annotation = field.getAnnotation(BindView.class);
            if (annotation != null) {
                BindView bindView = (BindView) annotation;
                int id = bindView.value();
                View view = rootView.findViewById(id);
                try {
                    field.setAccessible(true);
                    field.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}`

Send the Activity instance to the ViewBinder.

`public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtView)
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewBinder.bind(this);
        txtView.setText("Testing");
    }
    ...
}`

