import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.feeds.dataclass.TestResult
import com.utkarshnew.android.databinding.NewTestResultAdapterBinding

class NewTestResultAdapter : RecyclerView.Adapter<NewTestResultAdapter.NewCourseVH>() {

    var context: Context?=null
    var testResult: List<TestResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCourseVH {

        val view =   NewTestResultAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return NewCourseVH(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: NewCourseVH, position: Int) {
        holder.bind(testResult[position])

    }

    override fun getItemCount(): Int {
        return testResult.size
    }

    fun updateItems(testResultlist: List<TestResult>, ctx: Context?) {
        testResult =testResultlist
        context =ctx
    }

    inner class NewCourseVH(private  val newTestResultAdapterBinding: NewTestResultAdapterBinding) : RecyclerView.ViewHolder(newTestResultAdapterBinding.root) {
        fun bind(testResultdata: TestResult) {
            newTestResultAdapterBinding.livetestresult =testResultdata
        }
    }


}