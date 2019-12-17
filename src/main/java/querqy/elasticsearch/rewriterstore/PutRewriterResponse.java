package querqy.elasticsearch.rewriterstore;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

public class PutRewriterResponse extends ActionResponse implements StatusToXContentObject  {

    private IndexResponse indexResponse;
    private NodesReloadRewriterResponse reloadResponse;

    protected PutRewriterResponse() {}

    public PutRewriterResponse(final IndexResponse indexResponse, final NodesReloadRewriterResponse reloadResponse) {
        this.indexResponse = indexResponse;
        this.reloadResponse = reloadResponse;
    }

    public PutRewriterResponse(final StreamInput in) throws IOException {
        readFrom(in);
    }

    @Override
    public void readFrom(final StreamInput in) throws IOException {
        super.readFrom(in);
        indexResponse = new IndexResponse();
        indexResponse.readFrom(in);
        reloadResponse = new NodesReloadRewriterResponse(in);
    }

    @Override
    public void writeTo(final StreamOutput out) throws IOException {
        super.writeTo(out);
        indexResponse.writeTo(out);
        reloadResponse.writeTo(out);
    }

    @Override
    public RestStatus status() {
        return indexResponse.status();
    }

    @Override
    public XContentBuilder toXContent(final XContentBuilder builder, final Params params) throws IOException {

        builder.startObject();
        builder.field("put", indexResponse);
        builder.field("reloaded", reloadResponse);
        builder.endObject();
        return builder;
    }

    public IndexResponse getIndexResponse() {
        return indexResponse;
    }

    public NodesReloadRewriterResponse getReloadResponse() {
        return reloadResponse;
    }
}
