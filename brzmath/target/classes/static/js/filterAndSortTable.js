document.addEventListener('DOMContentLoaded', () => {

    const getSort = ({ target }) => {
        const order = (target.dataset.order = -(target.dataset.order || -1));
        const index = [...target.parentNode.cells].indexOf(target);
        const collator = new Intl.Collator(['en', 'ru'], { numeric: true });
        const comparator = (index, order) => (a, b) => order * collator.compare(
            a.children[index].innerHTML,
            b.children[index].innerHTML
        );

        for(const tBody of target.closest('table').tBodies)
            tBody.append(...[...tBody.rows].sort(comparator(index, order)));

        for(const cell of target.parentNode.cells)
            cell.classList.toggle('sorted', cell === target);
    };

    document.querySelectorAll('.table_sort thead').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));
});
function myFilter() {
    let filterSoc = document.getElementById('socialSelect').value;
    let filterBlock = document.getElementById('blockedSelect').value;
    let tr = document.getElementById("user-table").getElementsByTagName('tbody')[0]
        .getElementsByTagName("tr")
    for (let i = 0; i < tr.length; i++) {
        let tdSoc = tr[i].getElementsByTagName("td")[3];
        let tdBlock = tr[i].getElementsByTagName("td")[6];
        let txtSoc = tdSoc.textContent||tdSoc.innerText;
        let txtBlock = tdBlock.textContent||tdSoc.innerText;
        if ((filterSoc=='social'||txtSoc==filterSoc)
            &&(filterBlock=='status'||txtBlock==filterBlock)){
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }
}
function checkedDisplayed(elem){
    let trs = document.getElementById("user-table").getElementsByTagName('tbody')[0]
        .getElementsByTagName("tr")
    for(let i=0;i<trs.length;i++){
        if(trs[i].style.display!="none"){
            trs[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0].checked=elem.checked;
        }
    }
}