<div class="panel panel-default" id="ElementList">
    <div class="panel-heading">
        <h3 class="panel-title">Messages</h3>
    </div>
    <table class="table">
        <tbody>
            {{#each mData}}
            <tr>
                <td>{{this.mSubject}} </td>
                <td>Likes: {{this.mLikes}} </td>
                <td><button class="ElementList-editbtn" data-value="{{this.mId}}">Edit</button></td>
                <td><button class="ElementList-likebtn" data-value="{{this.mId}}">Like</button></td>
                <td><button class="ElementList-delbtn" data-value="{{this.mId}}">Delete</button></td>
            </tr>
            {{/each}}
        </tbody>
    </table>
</div>