<html>
<head>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
</head>
 
<body>
<form id="registro" action="#">
    Receta: <input type="text" name="recipeSearch" id="recipe_search"> 
</form>
<table>
<thead>
	<tr>
		<th><spring:message code="recipe.view"/>
		</th>
		<th><spring:message code="recipe.ticker"/>
		</th>
		<th><spring:message code="recipe.title"/>
		</th>
		<th><spring:message code="recipe.summary"/>
		</th>
		<th><spring:message code="recipe.lastUpdate"/>
		</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td id="identificator">
		</td>
		<td id="ticker">
		</td>
		<td id="title">
		</td>
		<td id="summary">
		</td>
		<td id="lastUpdate">
		</td>
	</tr>
</tbody>
</table>
<script type="text/javascript">
$('#recipe_search').keyup(
    function() {
        $.getJSON('loginDisponible.do', 
                  "login="+$('#recipe_search').val(),
                  function(obj) {
                     var id="";
                     var titulo="";
                     var ticke="";
                     var resume="";
                     var lastModified="";
					
                     for (var i=0; i<obj.ids.length; i++) {
                         id +="<a href='recipe/view.do?recipeId="+obj.ids[i]+"'>go</a>";
                         titulo += obj.titles[i];
                         ticke += obj.tickers[i];
                         resume += obj.summaries[i];
                         lastModified += obj.lastModifieds[i];
                  	 }
                     $('#identificator').html(id);
                     $('#ticker').html(ticke);
                     $('#title').html(titulo);
                     $('#summary').html(resume);
                     $('#lastUpdate').html(lastModified);
                  }
        );
    }   
);
</script>
	
</body>
</html>